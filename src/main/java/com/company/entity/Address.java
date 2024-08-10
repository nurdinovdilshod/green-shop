package com.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    private String house;

    private String floor;

    private String door;

    private String info;

    private String lon;

    private String lat;

    @Builder(builderMethodName = "childBuilder")
    public Address(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy,
                   boolean deleted, Integer id, String address, String house, String floor,
                   String door, String info, String lon, String lat) {
        super(createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.id = id;
        this.address = address;
        this.house = house;
        this.floor = floor;
        this.door = door;
        this.info = info;
        this.lon = lon;
        this.lat = lat;
    }
}