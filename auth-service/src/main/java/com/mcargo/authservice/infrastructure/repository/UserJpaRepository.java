package com.mcargo.authservice.infrastructure.repository;

import com.mcargo.authservice.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    // 조회 메서드
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByUserIdAndDeletedAtIsNull(Long userId);

    // 검색 메서드들
    // 사용자명, 이메일로 검색
    @Query("SELECT u FROM User u " +
        "WHERE (:username IS NULL OR u.username LIKE %:username%) " +
        "AND (:email IS NULL OR u.email LIKE %:email%)")
    Page<User> searchUsers(String username, String email, Pageable pageable);


    // 중복 체크 메서드들
    boolean existsByUsernameAndDeletedAtIsNull(String username);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);

    boolean existsByEmailAndDeletedAtIsNull(String email);


}
