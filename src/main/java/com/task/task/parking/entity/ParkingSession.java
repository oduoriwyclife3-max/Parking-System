package com.task.task.parking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_sessions")

public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long session_id;


    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) uuid = UUID.randomUUID();
    }

    @Column(nullable = false)
    private String vehicleRegNumber;

    @Column(nullable = false)
    private int slotNumber;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime entryTime = LocalDateTime.now();

    private LocalDateTime exitTime;

    private Double fee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SessionStatus status = SessionStatus.ACTIVE;

}
