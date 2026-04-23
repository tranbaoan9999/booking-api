package com.booking.repository;

import com.booking.common.enums.RoomStatus;
import com.booking.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
    SELECT DISTINCT r FROM Room r
    LEFT JOIN FETCH r.roomType rt
    LEFT JOIN FETCH rt.amenities
""")
    List<Room> findAllRooms(RoomStatus status);
}
