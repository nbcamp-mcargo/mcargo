package com.mcargo.authservice.infrastructure.repository;

import com.mcargo.authservice.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    // 조회 메서드
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByUserIdAndDeletedAtIsNull(Long userId);

    // 중복 체크 메서드들
    boolean existsByUsernameAndDeletedAtIsNull(String username);

    boolean existsByNicknameAndDeletedAtIsNull(String nickname);

    boolean existsByEmailAndDeletedAtIsNull(String email);


}
