package com.booking.domain.dto.room;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomTypeDto {
    private Long id;
    private String name;
    private Integer maxCapacity;
    private BigDecimal price;
}