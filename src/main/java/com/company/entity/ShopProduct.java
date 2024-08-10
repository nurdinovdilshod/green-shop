package com.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShopProduct extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer shopId;
    private Integer productId;

    @Builder(builderMethodName = "childBuilder")
    public ShopProduct(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                       boolean deleted, Integer id, Integer shopId, Integer productId) {
        super(createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.id = id;
        this.shopId = shopId;
        this.productId = productId;
    }
}
