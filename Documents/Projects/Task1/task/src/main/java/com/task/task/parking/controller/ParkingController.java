package com.task.task.parking.controller;

import com.task.task.parking.dto.ParkingSessionResponseDTO;
import com.task.task.parking.dto.ParkingSlotResponseDTO;
import com.task.task.parking.dto.PaymentRequestDTO;
import com.task.task.parking.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    // Get all parking slots status
    @GetMapping("/slots")
    public ResponseEntity<List<ParkingSlotResponseDTO>> getAllSlots() {
        return ResponseEntity.ok(parkingService.getAllSlots());
    }

    // Get all parking history / sessions
    @GetMapping("/history")
    public ResponseEntity<List<ParkingSessionResponseDTO>> getParkingHistory() {
        return ResponseEntity.ok(parkingService.getParkingHistory());
    }

    // Vehicle Entry (Allocates slot & creates active session)
    @PostMapping("/entry")
    public ResponseEntity<ParkingSessionResponseDTO> vehicleEntry(@RequestBody Map<String, String> request) {
        String regNumber = request.get("vehicleRegNumber");
        ParkingSessionResponseDTO session = parkingService.vehicleEntry(regNumber);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    // Calculate Exit Details (Records exit time & calculates fee)
    @PostMapping("/exit/calculate")
    public ResponseEntity<ParkingSessionResponseDTO> calculateExit(@RequestBody Map<String, String> request) {
        String regNumber = request.get("vehicleRegNumber");
        return ResponseEntity.ok(parkingService.calculateExitDetails(regNumber));
    }

    // Process Payment & Complete Session / Open Barrier
    @PostMapping("/exit/pay")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequestDTO request) {
        parkingService.processPaymentAndExit(request.getVehicleRegNumber(), request.isPaymentSuccess());
        return ResponseEntity.ok().build();
    }
}