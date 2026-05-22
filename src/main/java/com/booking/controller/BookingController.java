package com.booking.controller;

import com.booking.common.response.BaseResponse;
import com.booking.domain.dto.booking.BookingRequest;
import com.booking.domain.dto.booking.BookingResponse;
import com.booking.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/bookings")
    public BaseResponse<List<BookingResponse>> getAllBookings(
            @RequestParam(required = false) String status){
        return BaseResponse.success(bookingService.getAllBooking(status));
    }

    @PostMapping("/booking/new")
    public BaseResponse<BookingResponse> newBooking(
            @RequestBody BookingRequest request
    ){
        return BaseResponse.success(bookingService.newBooking(request));
    }
}
