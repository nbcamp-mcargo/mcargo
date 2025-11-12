package com.mcargo.common.auth.context;

import com.mcargo.common.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserContext {
    private final Long userId;
    private final List<UserRole> roles;

}