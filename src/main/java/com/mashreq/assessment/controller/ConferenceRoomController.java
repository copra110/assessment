package com.mashreq.assessment.controller;

import com.mashreq.assessment.config.AppConfig;
import com.mashreq.assessment.dto.BookingRequestDTO;
import com.mashreq.assessment.dto.ConferenceRoomDto;
import com.mashreq.assessment.model.Booking;
import com.mashreq.assessment.model.BookingDto;
import com.mashreq.assessment.model.ConferenceRoom;
import com.mashreq.assessment.service.ConferenceRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/conference")
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = conferenceRoomService;
    }

    @PostMapping("/book")
    public BookingDto bookRoom(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        return conferenceRoomService.bookRoom(bookingRequest);
    }

    @PostMapping("/available")
    public List<ConferenceRoomDto> getAvailableRooms(@Valid @RequestBody BookingRequestDTO bookingRequest)
    {
        return conferenceRoomService.getAvailableRooms(bookingRequest);
    }

    @GetMapping("/find-all-rooms")
    public List<ConferenceRoomDto> getAllRooms()
    {
        return conferenceRoomService.getAllRooms();
    }

    @GetMapping("/find-all-bookings")
    public List<BookingDto> getAllBookings()
    {
        return conferenceRoomService.getAllBookings();
    }
}
