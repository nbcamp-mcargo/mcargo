package com.mcargo.authservice.domain.repository;

import com.mcargo.authservice.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    // 기본 CRUD
    User save(User user);

    Optional<User> findById(Long userId);

    Page<User> findAll(Pageable pageable);

    void delete(User user);

    // 조회 메서드들
    Optional<User> findActiveByEmail(String email);

    Optional<User> findActiveById(Long userId);

    // 검색 메서드들
    // 사용자명, 이메일로 검색
    Page<User> searchUsers(String username, String email, Pageable pageable);

    // 중복 체크 메서드들
    boolean existsActiveByUsername(String username);

    boolean existsActiveByNickname(String nickname);

    boolean existsActiveByEmail(String email);
}
