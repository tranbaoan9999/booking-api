package com.booking.domain.dto.booking;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    Long guestId;
    Long roomId;
    LocalDate checkIn;
    LocalDate checkOut;
    Integer numberOfGuests;
}
