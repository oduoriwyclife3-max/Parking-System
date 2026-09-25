package com.task.task.parking.repository;

import com.task.task.parking.entity.ParkingSlot;
import com.task.task.parking.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    Optional<ParkingSlot> findByUuid(UUID uuid);

    Optional<ParkingSlot> findBySlotNumber(int slotNumber);

    List<ParkingSlot> findByStatus(SlotStatus status);
}
