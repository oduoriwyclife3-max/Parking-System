package com.task.task.parking.repository;

import com.task.task.parking.entity.ParkingSession;
import com.task.task.parking.entity.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    Optional<ParkingSession> findByUuid(UUID uuid);

    Optional<ParkingSession> findByVehicleRegNumberAndStatus(String vehicleRegNumber, SessionStatus status);

    List<ParkingSession> findAllByOrderByEntryTimeDesc();
}
