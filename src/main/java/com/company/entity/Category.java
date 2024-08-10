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
public class Category extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Builder(builderMethodName = "childBuilder")
    public Category(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                    boolean deleted, Integer id, String name, String description) {
        super(createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
