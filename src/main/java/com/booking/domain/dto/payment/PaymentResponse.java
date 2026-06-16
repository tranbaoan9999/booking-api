package com.booking.domain.dto.payment;

import com.booking.common.enums.PaymentMethod;
import com.booking.common.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long id;

    private Long bookingId;

    private PaymentMethod method;

    private PaymentStatus status;

    private BigDecimal amount;

    private LocalDateTime submittedAt;

    private LocalDateTime reviewedAt;
}