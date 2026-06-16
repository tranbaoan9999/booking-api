package com.booking.controller;

import com.booking.common.response.BaseResponse;
import com.booking.domain.dto.payment.PaymentRequest;
import com.booking.domain.dto.payment.PaymentResponse;
import com.booking.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payments")
    public BaseResponse<PaymentResponse> createPayment(
            @RequestBody PaymentRequest request
    ) {
        return BaseResponse.success(paymentService.createPayment(request));
    }

    @GetMapping("/payment/{id}")
    public BaseResponse<PaymentResponse> getPaymentById(
            @PathVariable Long id
    ) {
        return BaseResponse.success(paymentService.getPaymentById(id));
    }

    @PatchMapping("/payment/{id}/confirm")
    public BaseResponse<PaymentResponse> confirmPayment(
            @PathVariable Long id
    ) {
        return BaseResponse.success(paymentService.confirmPayment(id));
    }

    @PatchMapping("/payment/{id}/cancel")
    public BaseResponse<PaymentResponse> cancelPayment(
            @PathVariable Long id
    ) {
        return BaseResponse.success(paymentService.cancelPayment(id));
    }

    @GetMapping("/payments")
    public BaseResponse<List<PaymentResponse>> getAllPayments(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long bookingId
    ) {
        return BaseResponse.success(paymentService.getPayments(userId, bookingId));
    }
}
