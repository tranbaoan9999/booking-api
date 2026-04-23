package com.booking.service.room.impl;

import com.booking.common.exception.AppException;
import com.booking.domain.dto.room.RoomTypeDto;
import com.booking.domain.entity.RoomType;
import com.booking.repository.RoomRepository;
import com.booking.repository.RoomTypeRepository;
import com.booking.service.room.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;

    private RoomTypeDto toDto(RoomType roomType) {
        RoomTypeDto dto = new RoomTypeDto();
        dto.setId(roomType.getId());
        dto.setName(roomType.getName());
        dto.setMaxCapacity(roomType.getMaxCapacity());
        dto.setPrice(roomType.getPrice());
        return dto;
    }

    @Override
    public List<RoomTypeDto> getAllRoomTypes() {
        try{
            return roomTypeRepository.findAll().stream().map(this::toDto).toList();
        } catch (Exception e) {
        throw new AppException(400, e.getMessage());
        }
    }
}
