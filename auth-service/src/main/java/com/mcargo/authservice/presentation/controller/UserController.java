package com.mcargo.authservice.presentation.controller;

import com.mcargo.authservice.application.service.UserService;
import com.mcargo.authservice.domain.entity.UserStatus;
import com.mcargo.authservice.domain.response.UserResponseCode;
import com.mcargo.authservice.presentation.dto.request.UserDeleteRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserLoginRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserSignUpRequestDto;
import com.mcargo.authservice.presentation.dto.response.LogoutResponseDto;
import com.mcargo.authservice.presentation.dto.response.UserDeleteResponseDto;
import com.mcargo.authservice.presentation.dto.response.UserLoginResponseDto;
import com.mcargo.authservice.presentation.dto.response.UserSignUpResponseDto;
import com.mcargo.common.auth.context.annotation.CurrentUser;
import com.mcargo.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<Void> getUsers() {
        return ApiResponse.of(UserResponseCode.USER_LIST);
    }

    @GetMapping("/{userId}")
    public ApiResponse<Void> getUser(@PathVariable Long userId) {
        return ApiResponse.of(UserResponseCode.USER_ONE);
    }

    @GetMapping("/me")
    public ApiResponse<Void> getUserProfile(@CurrentUser Long userId) {

        return ApiResponse.of(UserResponseCode.USER_ME);
    }

    @PutMapping("/me")
    public ApiResponse<Void> updateUserProfile() {
        return ApiResponse.of(UserResponseCode.USER_UPDATE);
    }

    @DeleteMapping("/me")
    public ApiResponse<UserDeleteResponseDto> deleteUser(
        @CurrentUser Long userId,
        @Valid @RequestBody UserDeleteRequestDto requestDto) {
        // 인증된 사용자 ID로 회원 탈퇴 처리
        UserDeleteResponseDto responseDto = userService.deleteUser(userId, requestDto);
        return ApiResponse.of(UserResponseCode.USER_WITHDRAW, responseDto);
    }

    @GetMapping("/search")
    public ApiResponse<Void> searchUser(@RequestParam String username, @RequestParam String email) {
        return ApiResponse.of(UserResponseCode.USER_SEARCH);
    }

//    @PostMapping("/refresh")
//    public ApiResponse<Void> refreshToken(
//        @Valid @RequestBody RefreshTokenRequestDto requestDto,
//        HttpServletResponse response) {
//        RefreshTokenResponseDto responseDto = new RefreshTokenResponseDto();
//        return ApiResponse.of(null, responseDto);
//    }
}
