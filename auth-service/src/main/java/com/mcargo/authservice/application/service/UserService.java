package com.mcargo.authservice.application.service;

import com.mcargo.authservice.domain.entity.RefreshToken;
import com.mcargo.authservice.domain.entity.User;
import com.mcargo.authservice.domain.entity.UserStatus;
import com.mcargo.authservice.domain.exception.UserException;
import com.mcargo.authservice.domain.repository.RefreshTokenRepository;
import com.mcargo.authservice.domain.repository.UserRepository;
import com.mcargo.authservice.domain.response.UserResponseCode;
import com.mcargo.authservice.infrastructure.util.JwtUtil;
import com.mcargo.authservice.presentation.dto.request.UserDeleteRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserLoginRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserSignUpRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserUpdateRequestDto;
import com.mcargo.authservice.presentation.dto.response.*;
import com.mcargo.common.auth.UserRole;
import com.mcargo.common.util.PageingUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    //    private SecretKey secretKey;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;


    /**
     * 회원가입
     *
     * @param requestDto 회원가입정보 Dto
     * @return 사용자이름,닉네임,이메일,승인상태
     */
    @Transactional
    public UserSignUpResponseDto signUp(UserSignUpRequestDto requestDto) {
        User user = User.createUser(
            requestDto.username(),
            requestDto.nickname(),
            requestDto.email(),
            passwordEncoder.encode(requestDto.password()),
            requestDto.role()
        );
        userRepository.save(user);
        return new UserSignUpResponseDto(
            user.getUsername(),
            user.getNickname(),
            user.getEmail(),
            user.getStatus()
        );
    }

    // MASTER나 HUB_MANAGER 승인
    @Transactional
    public UserSignUpResponseDto approveUser(Long userId, Long approverId) {
        // 1 관리자인지 확인
        User manager = userRepository.findActiveById(approverId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        if (!(manager.getRole() == UserRole.MASTER || manager.getRole() == UserRole.HUB_MANAGER)) {
            throw new UserException(UserResponseCode.NOT_AUTHORIZED);
        }
        System.out.println("manager.getRole() = " + manager.getRole());

        User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        System.out.println("before user.getStatus() = " + user.getStatus());
        user.updateStatus(UserStatus.APPROVED, userId);
        System.out.println("after user.getStatus() = " + user.getStatus());

        userRepository.save(user);
        return new UserSignUpResponseDto(
            user.getUsername(),
            user.getNickname(),
            user.getEmail(),
            user.getStatus()
        );
    }

    private void validateDuplicateUser(UserSignUpRequestDto requestDto) {
        if (userRepository.existsActiveByUsername(requestDto.username())) {
            throw new UserException(UserResponseCode.DUPLICATE_USERNAME);
        }
        if (userRepository.existsActiveByNickname(requestDto.nickname())) {
            throw new UserException(UserResponseCode.DUPLICATE_NICKNAME);
        }
        if (userRepository.existsActiveByEmail(requestDto.email())) {
            throw new UserException(UserResponseCode.DUPLICATE_EMAIL);
        }
    }

    /**
     * 로그인
     *
     * @param requestDto ID && PASSWORD 정보
     * @return 액세스 토큰, 리프레시 토큰, 토큰유효기간, 사용자 정보 Dto
     */
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        // 1 이메일로 사용자 조회(탈퇴하지 않은 사용자만)
        User user = userRepository.findActiveByEmail(requestDto.email())
            .orElseThrow(() -> new UserException(UserResponseCode.INVALID_LOGIN_CREDENTIALS));
        // 2 사용자 상태 검증 (APPROVED 상태만 로그인 허용)
        if (user.getStatus() != UserStatus.APPROVED) {
            throw new UserException(UserResponseCode.NOT_AUTHORIZED);
        }
        // 3 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new UserException(UserResponseCode.INVALID_LOGIN_CREDENTIALS);
        }
        // 4 JWT 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user.getUserId(), user.getEmail(),
            user.getRole().name());
        String refreshToken = jwtUtil.createRefreshToken(user.getUserId());
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        // 5 기존 리프레시 토큰 삭제 후 새 토큰 저장
        refreshTokenRepository.deleteByUser(user);
        LocalDateTime expiresAt = LocalDateTime.now()
            .plusSeconds(jwtUtil.getRefreshTokenExpiration());
        RefreshToken refreshTokenEntity = new RefreshToken(refreshToken, user, expiresAt);
        refreshTokenRepository.save(refreshTokenEntity);
        UserInformationDto userInfo = new UserInformationDto(
            user.getUsername(), user.getNickname(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
        return new UserLoginResponseDto(
            accessToken, refreshToken, jwtUtil.getAccessTokenExpiration(), userInfo);
    }

    /**
     * 로그아웃
     *
     * @param userId 사용자 ID
     * @return 메시지, 시간
     */
    @Transactional
    public LogoutResponseDto logout(Long userId) {
        // 1 사용자 조회
        User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        System.out.println("user = " + user);
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getEmail() = " + user.getEmail());
        // 2 해당 사용자의 모든 리프레시 토큰 삭제 (토큰 무효화)
        if (user != null) {
            refreshTokenRepository.deleteByUser(user);
        }
        // 3 성공 응답 반환
        return new LogoutResponseDto("로그아웃 되었습니다.",
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    /**
     * 회원 목록 조회
     *
     * @param size
     * @param sortBy
     * @param isDescending
     * @return
     */
    @Transactional(readOnly = true)
    public Page<UserInformationDto> getAllUsers(int size, String sortBy, boolean isDescending) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);
        Page<User> pageUsers = userRepository.findAll(pageable);
        return pageUsers.map(
            user -> new UserInformationDto(
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            )
        );
    }

    @Transactional
    public UserInformationDto getUser(Long userId) {
        User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        return new UserInformationDto(
            user.getUsername(),
            user.getNickname(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    /**
     * 사용자명, 이메일로 사용자 검색
     *
     * @param username
     * @param email
     * @param size
     * @param sortBy
     * @param isDescending
     * @return
     */
    @Transactional(readOnly = true)
    public Page<UserInformationDto> searchUser(
        String username, String email, int size, String sortBy, boolean isDescending
    ) {
        Pageable pageable = PageingUtils.createPageable(size, sortBy, isDescending);
        Page<User> searchUsers = userRepository.searchUsers(username, email, pageable);
        return searchUsers.map(
            user -> new UserInformationDto(
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            )
        );
    }

    /**
     * 내 정보 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 정보 Dto
     */
    @Transactional(readOnly = true)
    public UserInformationDto getUserProfile(Long userId) {
        // 1 사용자 조회
        User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        log.info("my information print!!");
        log.info("user.getUsername() = " + user.getUsername());
        log.info("user.getEmail() = " + user.getEmail());
        log.info("user.getRole() = " + user.getRole());
        // 2 사용자 정보 객체에 정보 담아 반환
        return new UserInformationDto(
            user.getUsername(),
            user.getNickname(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    /**
     * 내 정보 수정
     *
     * @param userId     사용자 ID
     * @param requestDto 수정할 정보 Dto
     * @return 메시지, 사용자 정보 Dto
     */
    @Transactional
    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto requestDto) {
        // 1 사용자 조회
        User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        // 2 비밀번호 변경 검증
        if (requestDto.newPassword() != null && !requestDto.newPassword().isEmpty()) {
            validatePasswordChange(requestDto, user);
        }
        // 3 중복 데이터 검증
        validateDuplicateDataForUpdate(requestDto, user);
        // 4 필드 업데이트
        updateUserFields(requestDto, user);
        // 5 변경사항 저장
        User updatedUser = userRepository.save(user);
        UserInformationDto userInfo = new UserInformationDto(
            updatedUser.getUsername(), updatedUser.getNickname(), updatedUser.getEmail(),
            updatedUser.getRole(), updatedUser.getCreatedAt(), updatedUser.getUpdatedAt());
        // 6 응답 Dto 객체 생성
        return new UserUpdateResponseDto(
            "사용자 정보 수정했습니다.",
            userInfo
        );
    }

    /**
     * 회원 탈퇴
     *
     * @param userId     사용자ID
     * @param requestDto 삭제 전 비교할 정보 Dto
     * @return 삭제 메시지, 삭제시간, 삭제자
     */
    @Transactional
    public UserDeleteResponseDto deleteUser(Long userId,
                                            @Valid UserDeleteRequestDto requestDto) {
        // 1 사용자 조회
        User user = userRepository.findActiveById(userId)
            .orElseThrow(() -> new UserException(UserResponseCode.USER_NOT_FOUND));
        // 2 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new UserException(UserResponseCode.PASSWORD_MISMATCH);
        }
        // 3 소프트 삭제 처리
        user.delete(userId);
        // 4 관련 토큰 무효하
        if (user != null) {
            refreshTokenRepository.deleteByUser(user);
        }
        // 5 변경사항 저장
        User deleteUser = userRepository.save(user);
        // 6 응답 Dto 생성
        return new UserDeleteResponseDto(
            "회원 탈퇴 완료되었습니다.",
            deleteUser.getDeletedAt(),
            deleteUser.getUsername()
        );
    }

    /**
     * 비밀번호 변경 검증
     * - 새 비밀번호 제공 여부
     * - 새 비밀번호 제공했을 때 현재 비밀번호와 불일치 확인
     *
     * @param requestDto
     * @param user
     */
    private void validatePasswordChange(UserUpdateRequestDto requestDto, User user) {
        if (!(requestDto.curPassword() != null && !requestDto.curPassword().isEmpty())) {
            throw new UserException(UserResponseCode.CURRENT_PASSWORD_REQUIRED);
        }

        if (!passwordEncoder.matches(requestDto.curPassword(), user.getPassword())) {
            throw new UserException(UserResponseCode.CURRENT_PASSWORD_MISMATCH);
        }
    }

    /**
     * 현재 사용자 데이터와 다를 때만 중복 체크
     *
     * @param requestDto
     * @param curUser
     */
    private void validateDuplicateDataForUpdate(UserUpdateRequestDto requestDto, User curUser) {
        // 사용자명 중복 체크 (변경하려는 경우만)
        if (requestDto.username() != null &&
            !requestDto.username().equals(curUser.getUsername())) {
            if (userRepository.existsActiveByUsername(requestDto.username())) {
                throw new UserException(UserResponseCode.DUPLICATE_USERNAME);
            }
        }
        // 닉네임 중복 체크 (변경하려는 경우만)
        if (requestDto.nickname() != null &&
            !requestDto.nickname().equals(curUser.getNickname())) {
            if (userRepository.existsActiveByNickname(requestDto.nickname())) {
                throw new UserException(UserResponseCode.DUPLICATE_NICKNAME);
            }
        }
    }

    /**
     * 사용자 필드 업데이트
     *
     * @param requestDto
     * @param user
     */
    private void updateUserFields(UserUpdateRequestDto requestDto, User user) {
        // 비밀번호 암호화 처리
        String encodedPassword = null;
        if (requestDto.newPassword() != null && !requestDto.newPassword().isEmpty()) {
            encodedPassword = passwordEncoder.encode(requestDto.newPassword());
        }
        // User 엔티티에 update 메서드 사용
        user.updateUser(
            requestDto.username(),
            requestDto.nickname()
        );
    }

}
