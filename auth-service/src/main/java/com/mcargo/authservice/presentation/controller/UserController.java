package com.mcargo.authservice.presentation.controller;

import com.mcargo.authservice.application.service.UserService;
import com.mcargo.authservice.presentation.dto.request.UserLoginRequestDto;
import com.mcargo.authservice.presentation.dto.request.UserSignUpRequestDto;
import com.mcargo.authservice.presentation.dto.response.LogoutResponseDto;
import com.mcargo.authservice.presentation.dto.response.UserLoginResponseDto;
import com.mcargo.authservice.presentation.dto.response.UserSignUpResponseDto;
import com.mcargo.common.auth.annotation.CurrentUser;
import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.UserResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ApiResponse<UserSignUpResponseDto> signUp(
        @Valid @RequestBody UserSignUpRequestDto requestDto) {
        UserSignUpResponseDto responseDto = userService.signUp(requestDto);
        return ApiResponse.of(UserResponseCode.USER_CREATED, responseDto);
    }

    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDto> login(
        @Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ApiResponse.of(UserResponseCode.USER_LOGIN, responseDto);
    }

    @PostMapping("/logout")
    public ApiResponse<LogoutResponseDto> logout(@CurrentUser Long currentUser) {
        System.out.println("currentUser = " + currentUser);
        LogoutResponseDto responseDto = userService.logout(currentUser);
        return ApiResponse.of(UserResponseCode.USER_LOGOUT, responseDto);
    }

    @GetMapping
    public ApiResponse<Void> getUsers() {
        return ApiResponse.of(UserResponseCode.USER_LIST);
    }

    @GetMapping("/{userId}")
    public ApiResponse<Void> getUser(@PathVariable String userId) {
        return ApiResponse.of(UserResponseCode.USER_ONE);
    }

    @GetMapping("/me")
    public ApiResponse<Void> getUserProfile() {
        return ApiResponse.of(UserResponseCode.USER_ME);
    }

    @PutMapping("/me")
    public ApiResponse<Void> updateUserProfile() {
        return ApiResponse.of(UserResponseCode.USER_UPDATE);
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> deleteUser() {
        return ApiResponse.of(UserResponseCode.USER_WITHDRAW);
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
