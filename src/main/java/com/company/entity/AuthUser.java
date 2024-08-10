package com.company.entity;

import com.company.enums.UserRole;
import com.company.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthUser extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.NON_ACTIVE;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                    boolean deleted, String id, String name,String phoneNumber,
                    String password, UserRole role, UserStatus status) {
        super(createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
