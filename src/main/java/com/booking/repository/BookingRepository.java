package com.booking.repository;

import com.booking.common.enums.BookingStatus;
import com.booking.domain.dto.booking.BookingResponse;
import com.booking.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
    SELECT DISTINCT b FROM Booking b
    LEFT JOIN FETCH b.room r
    LEFT JOIN FETCH r.roomType
    LEFT JOIN FETCH b.guest
    WHERE b.status = :status
""")
    List<Booking> getAllBookingsByStatus(@Param("status") BookingStatus status);

    @Query("""
    SELECT DISTINCT b FROM Booking b
    LEFT JOIN FETCH b.room r
    LEFT JOIN FETCH r.roomType
    LEFT JOIN FETCH b.guest
""")
    List<Booking> getAllBookings();
}
