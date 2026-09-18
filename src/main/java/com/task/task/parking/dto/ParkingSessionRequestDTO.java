package com.task.task.parking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ParkingSessionRequestDTO {
    private String vehicleRegNumber;

    private int slotNumber;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Double fee;
}
