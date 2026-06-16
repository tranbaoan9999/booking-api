package com.booking.service.payment;

import com.booking.domain.dto.payment.PaymentRequest;
import com.booking.domain.dto.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse confirmPayment(Long id);

    PaymentResponse cancelPayment(Long id);

    List<PaymentResponse> getPayments(Long userId, Long bookingId);
}