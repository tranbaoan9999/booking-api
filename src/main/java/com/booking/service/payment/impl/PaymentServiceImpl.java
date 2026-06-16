package com.booking.service.payment.impl;

import com.booking.common.enums.PaymentStatus;
import com.booking.domain.dto.payment.PaymentRequest;
import com.booking.domain.dto.payment.PaymentResponse;
import com.booking.domain.entity.Booking;
import com.booking.domain.entity.Payment;
import com.booking.repository.BookingRepository;
import com.booking.repository.PaymentRepository;
import com.booking.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.booking.service.payment.PaymentMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow();

        Payment payment = paymentMapper.toEntity(request, booking);

        paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow();

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse confirmPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow();

        payment.setStatus(PaymentStatus.CONFIRMED);

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow();

        payment.setStatus(PaymentStatus.CANCELLED);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPayments(Long userId, Long bookingId) {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(paymentMapper::toResponse)
                .toList();
    }
}