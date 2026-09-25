package com.task.task.parking.service;

import com.task.task.parking.dto.ParkingSessionResponseDTO;
import com.task.task.parking.dto.ParkingSlotResponseDTO;
import com.task.task.parking.entity.*;
import com.task.task.parking.repository.ParkingSessionRepository;
import com.task.task.parking.repository.ParkingSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class ParkingService {
    private final ParkingSessionRepository sessionRepo;
    private final ParkingSlotRepository slotRepo;
    private static final double RATE_PER_HOUR = 150.0; // Adjustable hourly rate

    private ParkingSession findEntity(UUID uuid){
        return sessionRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Session Requested Is Unavailable"));
    }
    public List<ParkingSlotResponseDTO> getAllSlots() {
        return slotRepo.findAll().stream()
                .map(slot -> ParkingSlotResponseDTO.builder()
                        .uuid(slot.getUuid())
                        .slotNumber(slot.getSlotNumber())
                        .status(slot.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ParkingSessionResponseDTO> getParkingHistory() {
        return sessionRepo.findAllByOrderByEntryTimeDesc().stream()
                .map(this::mapToSessionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParkingSessionResponseDTO vehicleEntry(String vehicleRegNumber) {
        // Find an available parking slot
        ParkingSlot slot = slotRepo.findByStatus(SlotStatus.AVAILABLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Parking full. No slots available."));

        // Update slot status to occupied
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepo.save(slot);

        // Create parking session
        ParkingSession session = ParkingSession.builder()
                .vehicleRegNumber(vehicleRegNumber)
                .slotNumber(slot.getSlotNumber())
                .status(SessionStatus.ACTIVE)
                .build();

        ParkingSession savedSession = sessionRepo.save(session);
        return mapToSessionResponse(savedSession);
    }

    @Transactional
    public ParkingSessionResponseDTO calculateExitDetails(String vehicleRegNumber) {
        ParkingSession session = sessionRepo.findByVehicleRegNumberAndStatus(vehicleRegNumber, SessionStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active vehicle session not found for registration: " + vehicleRegNumber));

        session.setExitTime(LocalDateTime.now());

        // Calculate duration and fee
        long minutes = Duration.between(session.getEntryTime(), session.getExitTime()).toMinutes();
        double hours = Math.max(minutes / 60.0, 0.5); // Minimum billing threshold (e.g., 30 mins)
        double fee = hours * RATE_PER_HOUR;

        session.setFee(fee);
        ParkingSession updatedSession = sessionRepo.save(session);

        return mapToSessionResponse(updatedSession);
    }

    @Transactional
    public void processPaymentAndExit(String vehicleRegNumber, boolean paymentSuccess) {
        ParkingSession session = sessionRepo.findByVehicleRegNumberAndStatus(vehicleRegNumber, SessionStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active vehicle session not found for registration: " + vehicleRegNumber));

        if (!paymentSuccess) {
            throw new IllegalStateException("Payment Failed. Exit barrier remains closed.");
        }

        // Complete the session
        session.setStatus(SessionStatus.COMPLETED);
        sessionRepo.save(session);

        // Free up the corresponding parking slot
        ParkingSlot slot = slotRepo.findBySlotNumber(session.getSlotNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Parking slot not found: " + session.getSlotNumber()));

        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepo.save(slot);
    }

    private ParkingSessionResponseDTO mapToSessionResponse(ParkingSession session) {
        return ParkingSessionResponseDTO.builder()
                .uuid(session.getUuid())
                .vehicleRegNumber(session.getVehicleRegNumber())
                .slotNumber(session.getSlotNumber())
                .entryTime(session.getEntryTime())
                .exitTime(session.getExitTime())
                .fee(session.getFee())
                .status(session.getStatus())
                .build();
    }
}
