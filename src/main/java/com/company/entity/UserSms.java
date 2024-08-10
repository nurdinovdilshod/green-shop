package com.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserSms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;

    private Integer code;

    private LocalDateTime fromTime = LocalDateTime.now();

    private LocalDateTime toTime = LocalDateTime.now().plusMinutes(2);
}
