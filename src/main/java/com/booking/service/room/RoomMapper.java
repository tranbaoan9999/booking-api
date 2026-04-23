package com.booking.service.room;

import com.booking.domain.dto.room.RoomResponse;
import com.booking.domain.dto.room.RoomTypeDto;
import com.booking.domain.entity.Room;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public RoomResponse toResponse(Room room) {
        if (room == null) return null;

        RoomResponse res = new RoomResponse();

        res.setId(room.getId());
        res.setRoomNumber(room.getRoomNumber());

        if (room.getStatus() != null) {
            res.setStatus(room.getStatus().name());
        }

        if (room.getRoomType() != null) {
            RoomTypeDto typeDto = new RoomTypeDto();
            typeDto.setId(room.getRoomType().getId());
            typeDto.setName(room.getRoomType().getName());
            typeDto.setMaxCapacity(room.getRoomType().getMaxCapacity());
            typeDto.setPrice(room.getRoomType().getPrice());

            res.setRoomType(typeDto);

            if (room.getRoomType().getAmenities() != null) {
                Set<String> amenities = room.getRoomType()
                        .getAmenities()
                        .stream()
                        .map(a -> a.getName())
                        .collect(Collectors.toSet());
                res.setAmenities(amenities);
            }
        }
        return res;
    }
}