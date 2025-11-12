package com.mcargo.authservice.infrastructure.adapter;

import com.mcargo.authservice.domain.entity.User;
import com.mcargo.authservice.domain.repository.UserRepository;
import com.mcargo.authservice.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    // 필드 선언
    private final UserJpaRepository userJpaRepository;

    // 기본  CRUD
    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(user);
    }

    // 조회 메서드들
    @Override
    public Optional<User> findActiveByEmail(String email) {
        return userJpaRepository.findByEmailAndDeletedAtIsNull(email);
    }

    @Override
    public Optional<User> findActiveById(Long userId) {
        return userJpaRepository.findByUserIdAndDeletedAtIsNull(userId);
    }

    // 중복 체크 메서드들
    @Override
    public boolean existsActiveByUsername(String username) {
        return userJpaRepository.existsByUsernameAndDeletedAtIsNull(username);
    }

    @Override
    public boolean existsActiveByNickname(String nickname) {
        return userJpaRepository.existsByNicknameAndDeletedAtIsNull(nickname);
    }

    @Override
    public boolean existsActiveByEmail(String email) {
        return userJpaRepository.existsByEmailAndDeletedAtIsNull(email);
    }
}
