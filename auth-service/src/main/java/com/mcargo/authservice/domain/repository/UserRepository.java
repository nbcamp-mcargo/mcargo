package com.mcargo.authservice.domain.repository;

import com.mcargo.authservice.domain.entity.User;
import java.util.Optional;

public interface UserRepository {

    // 기본 CRUD
    User save(User user);

    Optional<User> findById(Long userId);

    void delete(User user);

    // 조회 메서드들
    Optional<User> findActiveByEmail(String email);

    Optional<User> findActiveById(Long userId);

    // 중복 체크 메서드들
    boolean existsActiveByUsername(String username);

    boolean existsActiveByNickname(String nickname);

    boolean existsActiveByEmail(String email);
}
