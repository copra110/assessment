package com.mashreq.assessment.service;

import com.mashreq.assessment.converter.BookingConverter;
import com.mashreq.assessment.converter.ConferenceRoomConverter;
import com.mashreq.assessment.dto.BookingRequestDTO;
import com.mashreq.assessment.dto.ConferenceRoomDto;
import com.mashreq.assessment.exceptions.NoAvailableRoomException;
import com.mashreq.assessment.model.Booking;
import com.mashreq.assessment.model.BookingDto;
import com.mashreq.assessment.model.ConferenceRoom;
import com.mashreq.assessment.repository.BookingRepository;
import com.mashreq.assessment.repository.ConferenceRoomRepository;
import com.mashreq.assessment.service.impl.ConferenceRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConferenceRoomServiceImplTest {

    @Mock
    private ConferenceRoomRepository conferenceRoomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingConverter bookingConverter;

    @Mock
    private ConferenceRoomConverter roomConverter;

    @InjectMocks
    private ConferenceRoomServiceImpl conferenceRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookRoom_Success() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setStartTime(LocalTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setEndTime(LocalTime.now().plusHours(2).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setNumberOfPeople(5);
        requestDTO.setOwner("John Doe");

        ConferenceRoom availableRoom = new ConferenceRoom();
        availableRoom.setId(1L);

        when(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), requestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(),requestDTO.getEndTime()),
                requestDTO.getNumberOfPeople()))
                .thenReturn(List.of(availableRoom));

        Booking newBooking = new Booking();
        when(bookingRepository.save(any())).thenReturn(newBooking);
        when(bookingConverter.parse(newBooking)).thenReturn(new BookingDto());

        BookingDto result = conferenceRoomService.bookRoom(requestDTO);

        assertNotNull(result);
        verify(conferenceRoomRepository, times(1)).findFirstAvailableRoomOrderedByCapacity(
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), requestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), requestDTO.getEndTime()),
                requestDTO.getNumberOfPeople());
        verify(bookingRepository, times(1)).save(any());
        verify(bookingConverter, times(1)).parse(newBooking);
    }

    @Test
    void bookRoom_NoAvailableRoomException() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setStartTime(LocalTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setEndTime(LocalTime.now().plusHours(2).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setNumberOfPeople(5);
        requestDTO.setOwner("John Doe");

        when(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), requestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), requestDTO.getEndTime()),
                requestDTO.getNumberOfPeople()))
                .thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(NoAvailableRoomException.class, () -> conferenceRoomService.bookRoom(requestDTO));
    }

    @Test
    void getAvailableRooms_Success() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setStartTime(LocalTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setEndTime(LocalTime.now().plusHours(2).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setNumberOfPeople(5);

        ConferenceRoom availableRoom = new ConferenceRoom();
        availableRoom.setId(1L);

        when(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), requestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), requestDTO.getEndTime()),
                requestDTO.getNumberOfPeople()))
                .thenReturn(List.of(availableRoom));

        when(roomConverter.parse(availableRoom)).thenReturn(new ConferenceRoomDto());

        List<ConferenceRoomDto> result = conferenceRoomService.getAvailableRooms(requestDTO);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(conferenceRoomRepository, times(1)).findFirstAvailableRoomOrderedByCapacity(
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), requestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), requestDTO.getEndTime()),
                requestDTO.getNumberOfPeople());
        verify(roomConverter, times(1)).parse(availableRoom);
    }

    @Test
    void getAvailableRooms_NoAvailableRoomException() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setStartTime(LocalTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setEndTime(LocalTime.now().plusHours(2).withMinute(0).withSecond(0).withNano(0));
        requestDTO.setNumberOfPeople(5);

        when(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                requestDTO.getStartTime(),
                requestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), requestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), requestDTO.getEndTime()),
                requestDTO.getNumberOfPeople()))
                .thenReturn(new ArrayList<>());

        assertThrows(NoAvailableRoomException.class, () -> conferenceRoomService.getAvailableRooms(requestDTO));
    }

    @Test
    void getAllRooms() {
        List<ConferenceRoom> rooms = List.of(new ConferenceRoom(), new ConferenceRoom());
        when(conferenceRoomRepository.findAll()).thenReturn(rooms);
        when(roomConverter.parse(any())).thenReturn(new ConferenceRoomDto());
        List<ConferenceRoomDto> result = conferenceRoomService.getAllRooms();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(rooms.size(), result.size());
        verify(conferenceRoomRepository, times(1)).findAll();
        verify(roomConverter, times(rooms.size())).parse(any());
    }

    @Test
    void getAllBookings() {
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);
        when(bookingConverter.parse(any())).thenReturn(new BookingDto());
        List<BookingDto> result = conferenceRoomService.getAllBookings();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(bookings.size(), result.size());
        verify(bookingRepository, times(1)).findAll();
        verify(bookingConverter, times(bookings.size())).parse(any());
    }
}
