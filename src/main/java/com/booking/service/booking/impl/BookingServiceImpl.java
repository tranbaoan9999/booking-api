package com.booking.service.booking.impl;

import com.booking.common.enums.BookingStatus;
import com.booking.common.exception.AppException;
import com.booking.domain.dto.booking.BookingRequest;
import com.booking.domain.dto.booking.BookingResponse;
import com.booking.domain.entity.Booking;
import com.booking.domain.entity.Guest;
import com.booking.domain.entity.Room;
import com.booking.repository.BookingRepository;
import com.booking.repository.GuestRepository;
import com.booking.repository.RoomRepository;
import com.booking.service.booking.BookingMapper;
import com.booking.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingMapper bookingMapper;

    private BookingStatus parseStatus(String status) {
        try {
            return BookingStatus.valueOf(status.trim().toUpperCase());
        } catch (Exception e) {
            throw new AppException(400,"Invalid booking status: " + status);
        }
    }

    @Override
    public List <BookingResponse> getAllBooking(String status){
        List<Booking> listBooking;

        if (status != null && !status.isBlank()) {
            listBooking = bookingRepository.getAllBookingsByStatus(parseStatus(status));
        } else {
            listBooking = bookingRepository.getAllBookings();
        }

        return listBooking.stream().map(bookingMapper::toResponse).toList();
    }

    @Override
    public BookingResponse newBooking(BookingRequest request) {
        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new AppException(400, "checkOut must be after checkIn");
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(400, "Room not found"));

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new AppException(400, "Guest not found, Please login to continue"));

        System.out.println(bookingRepository.existsOverlappingBooking(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut()
        ));

        if (bookingRepository.existsOverlappingBooking(
                request.getRoomId(),
                request.getCheckIn(),
                request.getCheckOut()
        )) {
            throw new AppException(409, "Room is already booked in this time range");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setCheckIn(request.getCheckIn());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setStatus(BookingStatus.valueOf("PENDING"));
        booking.setGuest(guest);

        long nights = DAYS.between(
                request.getCheckIn(),
                request.getCheckOut()
        );

        booking.setTotalPrice(
                room.getRoomType().getPrice().multiply(BigDecimal.valueOf(nights))
        );
        bookingRepository.save(booking);
        return bookingMapper.toResponse(booking);
    }

    @Override
    public BookingResponse getBookingDetail(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new  AppException(404, "Booking not found"));

        return bookingMapper.toResponse(booking);
    }
}
