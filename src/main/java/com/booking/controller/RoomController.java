package com.booking.controller;


import com.booking.common.response.BaseResponse;
import com.booking.domain.dto.room.RoomResponse;
import com.booking.domain.dto.room.RoomTypeDto;
import com.booking.service.room.RoomService;
import com.booking.service.room.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;

    @GetMapping("/rooms")
    public BaseResponse<List<RoomResponse>> getAllRooms(
            @RequestParam(required = false) String status
    ){
        return BaseResponse.success(roomService.getAllRooms(status));
    }

    @GetMapping("/room/{id}")
    public BaseResponse<RoomResponse> getRoomById(
        @PathVariable String id
    ){
        return BaseResponse.success(roomService.getRoomById(id));
    }

    @GetMapping("/room-types")
    public BaseResponse<List<RoomTypeDto>> getAllRoomTypes(
    ){
        return BaseResponse.success(roomTypeService.getAllRoomTypes());
    }

}
