package com.booking.domain.dto.room;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String roomNumber;
    private RoomTypeDto roomType;
}
