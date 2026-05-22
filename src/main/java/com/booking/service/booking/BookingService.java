package com.booking.service.booking;

import com.booking.domain.dto.booking.BookingRequest;
import com.booking.domain.dto.booking.BookingResponse;

import java.util.List;

public interface BookingService {
    public List<BookingResponse> getAllBooking(String status);
    BookingResponse newBooking(BookingRequest request);
}