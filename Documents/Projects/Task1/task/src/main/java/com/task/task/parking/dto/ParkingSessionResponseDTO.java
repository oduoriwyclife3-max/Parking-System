package com.task.task.parking.dto;

import com.task.task.parking.entity.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParkingSessionResponseDTO {
    private UUID uuid;

    private String vehicleRegNumber;

    private int slotNumber;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Double fee;

    private SessionStatus status;
}
