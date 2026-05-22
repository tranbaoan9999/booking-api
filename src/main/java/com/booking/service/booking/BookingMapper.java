package com.booking.service.booking;

import com.booking.domain.dto.Guest.GuestDto;
import com.booking.domain.dto.booking.BookingResponse;
import com.booking.domain.dto.room.RoomDto;
import com.booking.domain.dto.room.RoomTypeDto;
import com.booking.domain.entity.Booking;
import com.booking.domain.entity.Guest;
import com.booking.domain.entity.Room;
import com.booking.domain.entity.RoomType;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking booking) {
        BookingResponse res = new BookingResponse();
        if(booking == null){
            return null;
        }
        res.setId(booking.getId());
        res.setCheckIn(booking.getCheckIn());
        res.setCheckOut(booking.getCheckOut());
        res.setNumberOfGuests(booking.getNumberOfGuests());
        res.setStatus(booking.getStatus().name());
        res.setTotalPrice(booking.getTotalPrice());
        res.setCreatedAt(booking.getCreatedAt());
        res.setUpdatedAt(booking.getUpdatedAt());

        if(booking.getRoom() != null){
            res.setRoom(mapRoom(booking.getRoom()));
        }
        if(booking.getGuest() != null){
            res.setGuest(mapGuest(booking.getGuest()));
        }
        return res;
    }

    private RoomDto mapRoom(Room room){
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());

        if (room.getRoomType() != null) {
            dto.setRoomType(mapRoomType(room.getRoomType()));
        }
        return dto;
    }

    private RoomTypeDto mapRoomType(RoomType roomType){
        RoomTypeDto dto = new RoomTypeDto();

        dto.setId(roomType.getId());
        dto.setName(roomType.getName());
        dto.setMaxCapacity(roomType.getMaxCapacity());
        dto.setPrice(roomType.getPrice());
        return dto;
    }

    private GuestDto mapGuest(Guest guest) {
        GuestDto dto = new GuestDto();

        dto.setId(guest.getId());
        dto.setEmail(guest.getEmail());
        dto.setPhone(guest.getPhone());
        dto.setName(guest.getName());
        return dto;
    }
}
