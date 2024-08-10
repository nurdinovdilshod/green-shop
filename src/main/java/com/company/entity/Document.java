package com.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalName;

    private String generatedName;

    private String uuid = UUID.randomUUID().toString();

    private Long size;

    private String extension;

    private String contentType;

    private String url;

    @Builder(builderMethodName = "childBuilder")
    public Document(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                    boolean deleted, Integer id, String originalName, String generatedName, Long size,
                    String extension, String contentType, String url, String uuid) {
        super(createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.id = id;
        this.originalName = originalName;
        this.generatedName = generatedName;
        this.size = size;
        this.extension = extension;
        this.contentType = contentType;
        this.url = url;
        this.uuid=uuid;
    }
}
