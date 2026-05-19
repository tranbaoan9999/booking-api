package com.booking.domain.dto.booking;

import com.booking.domain.dto.Guest.GuestDto;
import com.booking.domain.dto.room.RoomDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer numberOfGuests;
    private RoomDto room;
    private GuestDto guest;
    private String status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
