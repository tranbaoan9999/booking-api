package com.booking.domain.dto.room;

import lombok.Data;

@Data
public class RoomRequest {
    private String roomNumber;
    private String status;
    private String roomTypeId;
}
