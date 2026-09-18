package com.task.task.parking.dto;

import com.task.task.parking.entity.SlotStatus;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParkingSlotResponseDTO {
    private UUID uuid;
    private int slotNumber;
    private SlotStatus status;
}
