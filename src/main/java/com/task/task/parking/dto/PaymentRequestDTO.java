package com.task.task.parking.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDTO {
    private String vehicleRegNumber;
    private boolean paymentSuccess;
}