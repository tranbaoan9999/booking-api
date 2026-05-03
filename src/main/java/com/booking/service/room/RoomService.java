package com.booking.service.room;

import com.booking.domain.dto.room.RoomRequest;
import com.booking.domain.dto.room.RoomResponse;

import java.util.List;

public interface RoomService {
    List<RoomResponse> getAllRooms(String status);
    RoomResponse getRoomById(String id);
    RoomResponse createRoom(RoomRequest roomRequest);
}
