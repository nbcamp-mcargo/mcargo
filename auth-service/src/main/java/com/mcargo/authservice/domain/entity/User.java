package com.mcargo.authservice.domain.entity;

import com.mcargo.common.auth.UserRole;
import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "p_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "nickname", nullable = false, unique = true, length = 30)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

//    @Column(name = "address", nullable = false)
//    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    // 정적 메서드로 User 생성
    public static User createUser(String username, String nickname,
                                  String email, String password, UserRole role) {
        User user = new User();
        user.username = username;
        user.nickname = nickname;
        user.email = email;
        user.password = password;
        user.role = role;

        // MASTER, HUB_MANAGER 는 바로 APPROVED, 나머지는 PENDING
        if (role == UserRole.MASTER || role == UserRole.HUB_MANAGER) {
            user.status = UserStatus.APPROVED;
        } else {
            user.status = UserStatus.PENDING;
        }
        return user;
    }

    public void updateStatus(UserStatus status, Long apporvedBy) {
        this.status = status;
        this.createdBy = apporvedBy;
    }

    public void delete(Long userId) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userId;
    }

    /**
     * 사용자 정보 업데이트
     */
    public void updateUser(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    /**
     * 사용자 비밀번호 변경
     */
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
