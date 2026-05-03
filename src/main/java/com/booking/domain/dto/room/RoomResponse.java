package com.booking.domain.dto.room;

import lombok.Data;
import java.util.Set;

@Data
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private String status;
    private RoomTypeDto roomType;
    private Set<String> amenities;
}