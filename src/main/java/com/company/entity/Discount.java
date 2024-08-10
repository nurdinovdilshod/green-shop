package com.company.entity;

import com.company.enums.DiscountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productId;

    private Long amount;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DiscountStatus status = DiscountStatus.AVAILABLE;
}
