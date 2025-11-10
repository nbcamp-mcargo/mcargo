package com.mcargo.authservice.application.service;

import com.mcargo.authservice.JwtUtil;
import com.mcargo.authservice.domain.entity.RefreshToken;
import com.mcargo.authservice.domain.entity.User;
import com.mcargo.authservice.domain.repository.RefreshTokenRepository;
import com.mcargo.authservice.domain.repository.UserRepository;
import com.mcargo.authservice.presentation.dto.request.UserLoginRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserSignUpRequestDto;
import com.mcargo.authservice.presentation.dto.response.UserInformationDto;
import com.mcargo.authservice.presentation.dto.response.UserLoginResponseDto;
import com.mcargo.authservice.presentation.dto.response.UserSignUpResponseDto;
import com.mcargo.common.exception.UserException;
import com.mcargo.common.response.UserResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    //    private SecretKey secretKey;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;


    // 회원 가입
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
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getNickname() = " + user.getNickname());
        System.out.println("user.getEmail() = " + user.getEmail());
        return new UserSignUpResponseDto(
            user.getUsername(),
            user.getNickname(),
            user.getEmail()
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

    // 로그인
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        // 1 이메일로 사용자 조회(탈퇴하지 않은 사용자만)
        User user = userRepository.findActiveByEmail(requestDto.email())
            .orElseThrow(() -> new UserException(UserResponseCode.INVALID_LOGIN_CREDENTIALS));
        // 2 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new UserException(UserResponseCode.INVALID_LOGIN_CREDENTIALS);
        }
        // 3 JWT 토큰 생성
        String accessToken = jwtUtil.createAccessToken(user.getUserId(), user.getEmail(),
            user.getRole().name());
        String refreshToken = jwtUtil.createRefreshToken(user.getUserId());
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        // 4 기존 리프레시 토큰 삭제 후 새 토큰 저장
        refreshTokenRepository.deleteByUser(user);
        LocalDateTime expiresAt = LocalDateTime.now()
            .plusSeconds(jwtUtil.getRefreshTokenExpiration());
        RefreshToken refreshTokenEntity = new RefreshToken(refreshToken, user, expiresAt);
        refreshTokenRepository.save(refreshTokenEntity);
        UserInformationDto userInfo = new UserInformationDto(
            user.getUsername(), user.getNickname(), user.getEmail(), user.getRole());
        return new UserLoginResponseDto(
            accessToken, refreshToken, jwtUtil.getAccessTokenExpiration(), userInfo);
    }

//    // 로그아웃
//    @Transactional
//    public LogoutResponseDto logout(Long userId) {
//        // 1 사용자 조회
//        User user = userRepository.findActiveById(userId)
//            .orElseThrow(() -> null);
//        System.out.println("user = " + user);
//        System.out.println("user.getUsername() = " + user.getUsername());
//        System.out.println("user.getEmail() = " + user.getEmail());
//        // 2 해당 사용자의 모든 리프레시 토큰 삭제 (토큰 무효화)
//        if (user != null) {
//            refreshTokenRepository.deleteByUser(user);
//        }
//        // 3 성공 응답 반환
//        return new LogoutResponseDto("로그아웃 되었습니다.",
//            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//    }


}
