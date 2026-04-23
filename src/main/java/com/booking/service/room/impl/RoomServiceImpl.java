package com.booking.service.room.impl;

import com.booking.common.enums.BookingStatus;
import com.booking.common.enums.RoomStatus;
import com.booking.common.exception.AppException;
import com.booking.domain.dto.room.RoomResponse;
import com.booking.domain.dto.room.RoomTypeDto;
import com.booking.domain.entity.Room;
import com.booking.repository.RoomRepository;
import com.booking.service.room.RoomMapper;
import com.booking.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    private RoomStatus parseStatus(String status) {
        try {
            return RoomStatus.valueOf(status.trim().toUpperCase());
        } catch (Exception e) {
            throw new AppException(400,"Invalid booking status: ");
        }
    }

    @Override
    public List<RoomResponse> getAllRooms(String status) {
        List<Room> rooms;

        if(status != null) {
            rooms = roomRepository.findAllRooms(parseStatus(status));
        } else{
            rooms = roomRepository.findAll();
        }
        return rooms.stream().map(roomMapper::toResponse).toList();
    }

    @Override
    public RoomResponse getRoomById(String id) {
        Room room = roomRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new AppException(404, "Room not found"));

        return roomMapper.toResponse(room);
    }
}
