package com.booking.repository;

import com.booking.common.enums.RoomStatus;
import com.booking.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
    SELECT DISTINCT r FROM Room r
    LEFT JOIN FETCH r.roomType rt
    LEFT JOIN FETCH rt.amenities
""")
    List<Room> findAllRooms(RoomStatus status);

    Optional<Room> findByRoomNumber(String roomNumber);

    @Query("""
        SELECT r
        FROM Room r
        JOIN r.roomType rt

        WHERE r.status = 'AVAILABLE'
            
         AND rt.maxCapacity >= :guest

        AND r.id NOT IN (
            SELECT b.room.id
            FROM Booking b
            WHERE b.status IN (
                'PENDING',
                'CONFIRMED'
            )
            AND b.checkIn < :checkout
            AND b.checkOut > :checkin
        )
    """)
    List<Room> findAvailableRooms(
            LocalDate checkin,
            LocalDate checkout,
            Integer guest
    );
}
