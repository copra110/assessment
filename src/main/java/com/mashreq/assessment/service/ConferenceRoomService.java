package com.mashreq.assessment.service;

import com.mashreq.assessment.dto.BookingRequestDTO;
import com.mashreq.assessment.dto.ConferenceRoomDto;
import com.mashreq.assessment.model.BookingDto;
import com.mashreq.assessment.model.ConferenceRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceRoomService {
    BookingDto bookRoom(BookingRequestDTO bookingRequest);

    List<ConferenceRoomDto> getAvailableRooms(BookingRequestDTO bookingRequestDTO);

    List<ConferenceRoomDto> getAllRooms();

    List<BookingDto> getAllBookings();
}
