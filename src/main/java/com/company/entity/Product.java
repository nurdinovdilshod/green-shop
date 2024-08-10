package com.company.entity;

import com.company.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer categoryId;

    private String description;

    private Integer count;

    private Double price;

    private Integer documentId;

    private Boolean hasDiscount;

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.AVAILABLE;

    @Builder(builderMethodName = "childBuilder")
    public Product(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                   boolean deleted, Integer id, String name, Integer categoryId, String description,
                   Integer count, Double price, Integer documentId, Boolean hasDiscount, ProductStatus status) {
        super(createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        this.count = count;
        this.price = price;
        this.documentId = documentId;
        this.hasDiscount = hasDiscount;
        this.status = status;
    }
}
