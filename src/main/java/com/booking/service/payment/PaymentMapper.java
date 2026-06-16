package com.booking.service.payment;

import com.booking.common.enums.PaymentStatus;
import com.booking.domain.dto.payment.PaymentRequest;
import com.booking.domain.dto.payment.PaymentResponse;
import com.booking.domain.entity.Booking;
import com.booking.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest request, Booking booking) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setSubmittedAt(LocalDateTime.now());

        return payment;
    }

    public PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());
        response.setBookingId(payment.getBooking().getId());
        response.setAmount(payment.getAmount());
        response.setMethod(payment.getMethod());
        response.setStatus(payment.getStatus());
        response.setSubmittedAt(payment.getSubmittedAt());

        return response;
    }
}