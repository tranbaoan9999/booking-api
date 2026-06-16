package com.booking.domain.dto.payment;

import com.booking.common.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long bookingId;
    private PaymentMethod method;
    private BigDecimal amount;
}
