package com.booking.service.room.impl;

import com.booking.common.enums.BookingStatus;
import com.booking.common.enums.RoomStatus;
import com.booking.common.exception.AppException;
import com.booking.domain.dto.room.RoomRequest;
import com.booking.domain.dto.room.RoomResponse;
import com.booking.domain.dto.room.RoomTypeDto;
import com.booking.domain.entity.Room;
import com.booking.domain.entity.RoomType;
import com.booking.repository.RoomRepository;
import com.booking.repository.RoomTypeRepository;
import com.booking.service.room.RoomMapper;
import com.booking.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomTypeRepository roomTypeRepository;

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

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        roomRepository.findByRoomNumber(roomRequest.getRoomNumber())
                .ifPresent(r -> {
                    throw new AppException(400, "Room number already exists");
                });

        validateRequest(roomRequest);

        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setStatus(parseStatus(roomRequest.getStatus()));

        Long roomTypeId = Long.valueOf(roomRequest.getRoomTypeId());
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new AppException(404, "Room type not found"));

        room.setRoomType(roomType);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponse(savedRoom);
    }

    @Override
    public List<RoomResponse> getAvailableRooms(LocalDate checkin, LocalDate checkout, Integer guest) {
        if (!checkout.isAfter(checkin)) {
            throw new AppException( 400,
                    "Checkout date must be after checkin date"
            );
        }
        List<Room> rooms = roomRepository.findAvailableRooms(checkin, checkout, guest);
        return rooms.stream().map(roomMapper::toResponse).toList();

    }

    private void validateRequest(RoomRequest request) {
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            throw new AppException(400, "Status is required");
        }

        if (request.getRoomTypeId() == null || request.getRoomTypeId().isBlank()) {
            throw new AppException(400, "Room type id is required");
        }
    }
}
