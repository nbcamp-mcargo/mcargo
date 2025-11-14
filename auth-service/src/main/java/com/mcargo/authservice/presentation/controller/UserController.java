package com.mcargo.authservice.presentation.controller;

import com.mcargo.authservice.application.service.UserService;
import com.mcargo.authservice.domain.entity.UserStatus;
import com.mcargo.authservice.domain.response.UserResponseCode;
import com.mcargo.authservice.presentation.dto.request.UserDeleteRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserLoginRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserSignUpRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserUpdateRequestDto;
import com.mcargo.authservice.presentation.dto.response.*;
import com.mcargo.common.auth.context.annotation.CurrentUser;
import com.mcargo.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ApiResponse<UserSignUpResponseDto> signUp(
        @Valid @RequestBody UserSignUpRequestDto requestDto) {
        UserSignUpResponseDto responseDto = userService.signUp(requestDto);
        if (responseDto.status() == UserStatus.PENDING) {
            return ApiResponse.of(UserResponseCode.USER_PENDING, responseDto);
        }
        return ApiResponse.of(UserResponseCode.USER_CREATED, responseDto);
    }

    @PostMapping("/approve/{userId}")
    public ApiResponse<UserSignUpResponseDto> approveUser(@PathVariable Long userId, @CurrentUser Long approverId) {
        UserSignUpResponseDto responseDto = userService.approveUser(userId, approverId);
        return ApiResponse.of(UserResponseCode.USER_CREATED, responseDto);
    }

    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDto> login(
        @Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ApiResponse.of(UserResponseCode.USER_LOGIN, responseDto);
    }

    @PostMapping("/logout")
    public ApiResponse<LogoutResponseDto> logout(@CurrentUser Long userId) {
        System.out.println("currentUser = " + userId);
        LogoutResponseDto responseDto = userService.logout(userId);
        return ApiResponse.of(UserResponseCode.USER_LOGOUT, responseDto);
    }

    @GetMapping
    public ApiResponse<Page<UserInformationDto>> getAllUsers(
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "true") Boolean isDescending
    ) {
        Page<UserInformationDto> responseDto = userService.getAllUsers(size, sortBy, isDescending);
        return ApiResponse.of(UserResponseCode.USER_LIST, responseDto);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserInformationDto> getUser(@PathVariable Long userId) {
        UserInformationDto responseDto = userService.getUser(userId);
        return ApiResponse.of(UserResponseCode.USER_ONE, responseDto);
    }

    @GetMapping("/me")
    public ApiResponse<UserInformationDto> getUserProfile(@CurrentUser Long userId) {
        UserInformationDto responseDto = userService.getUserProfile(userId);
        log.info("getUserProfile END");
        return ApiResponse.of(UserResponseCode.USER_ME, responseDto);
    }

    @PutMapping("/me")
    public ApiResponse<UserUpdateResponseDto> updateUserProfile(
        @CurrentUser Long userId,
        @Valid @RequestBody UserUpdateRequestDto requestDto) {
        UserUpdateResponseDto responseDto = userService.updateUser(userId, requestDto);
        return ApiResponse.of(UserResponseCode.USER_UPDATE, responseDto);
    }

    @DeleteMapping("/me")
    public ApiResponse<UserDeleteResponseDto> deleteUser(
        @CurrentUser Long userId,
        @Valid @RequestBody UserDeleteRequestDto requestDto) {
        // 인증된 사용자 ID로 회원 탈퇴 처리
        UserDeleteResponseDto responseDto = userService.deleteUser(userId, requestDto);
        return ApiResponse.of(UserResponseCode.USER_WITHDRAW, responseDto);
    }

    // 사용자명, 이메일로 회원 검색
    @GetMapping("/search")
    public ApiResponse<Page<UserInformationDto>> searchUser(
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String email,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "true") Boolean isDescending
    ) {
        Page<UserInformationDto> responseDto = userService.searchUser(
            username, email, size, sortBy, isDescending);
        return ApiResponse.of(UserResponseCode.USER_SEARCH, responseDto);
    }

//    @PostMapping("/refresh")
//    public ApiResponse<Void> refreshToken(
//        @Valid @RequestBody RefreshTokenRequestDto requestDto,
//        HttpServletResponse response) {
//        RefreshTokenResponseDto responseDto = new RefreshTokenResponseDto();
//        return ApiResponse.of(null, responseDto);
//    }
}
