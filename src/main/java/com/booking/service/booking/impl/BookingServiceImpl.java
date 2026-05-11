package com.booking.service.booking.impl;

import com.booking.common.enums.BookingStatus;
import com.booking.common.exception.AppException;
import com.booking.domain.dto.booking.BookingResponse;
import com.booking.domain.entity.Booking;
import com.booking.repository.BookingRepository;
import com.booking.service.booking.BookingMapper;
import com.booking.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    private BookingStatus parseStatus(String status) {
        try {
            return BookingStatus.valueOf(status.trim().toUpperCase());
        } catch (Exception e) {
            throw new AppException(400,"Invalid booking status: " + status);
        }
    }

    @Override
    public List <BookingResponse> getAllBooking(String status){
        List<Booking> listBooking;

        if (status != null && !status.isBlank()) {
            listBooking = bookingRepository.getAllBookingsByStatus(parseStatus(status));
        } else {
            listBooking = bookingRepository.getAllBookings();
        }

        return listBooking.stream().map(bookingMapper::toResponse).toList();
    }
}
