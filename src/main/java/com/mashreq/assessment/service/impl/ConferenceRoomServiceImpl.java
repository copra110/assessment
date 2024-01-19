package com.mashreq.assessment.service.impl;

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
import com.mashreq.assessment.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceRoomServiceImpl implements ConferenceRoomService
{

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingConverter bookingConverter;

    @Autowired
    private ConferenceRoomConverter roomConverter;

    @Override
    public BookingDto bookRoom(BookingRequestDTO bookingRequestDTO) {
        List<ConferenceRoom> availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                bookingRequestDTO.getStartTime(),
                bookingRequestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), bookingRequestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), bookingRequestDTO.getEndTime()),
                bookingRequestDTO.getNumberOfPeople());


        if (availableRooms.isEmpty()) {
            throw new NoAvailableRoomException("No available room to book at this time.");
        }
        ConferenceRoom room = availableRooms.isEmpty() ? null : availableRooms.get(0);
        Booking newBooking = new Booking();
        newBooking.setRoom(room);
        newBooking.setStartTime(LocalDateTime.of(LocalDate.now(), bookingRequestDTO.getStartTime()));
        newBooking.setEndTime(LocalDateTime.of(LocalDate.now(), bookingRequestDTO.getEndTime()));
        newBooking.setNumberOfPeople(bookingRequestDTO.getNumberOfPeople());
        newBooking.setOwner(bookingRequestDTO.getOwner());

        newBooking=bookingRepository.save(newBooking);;
        return bookingConverter.parse(newBooking);
    }

    @Override
    public List<ConferenceRoomDto> getAvailableRooms(BookingRequestDTO bookingRequestDTO) {
        List<ConferenceRoom> availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                bookingRequestDTO.getStartTime(),
                bookingRequestDTO.getEndTime(),
                LocalDateTime.of(LocalDate.now(), bookingRequestDTO.getStartTime()),
                LocalDateTime.of(LocalDate.now(), bookingRequestDTO.getEndTime()),
                bookingRequestDTO.getNumberOfPeople());

        if (availableRooms.isEmpty()) {
            throw new NoAvailableRoomException("Requested booking timings conflicts with existing bookings or maintenance timings please check avaliable meeting timings and update accordingly");
        }

        return availableRooms.stream().map(conferenceRoom -> roomConverter.parse(conferenceRoom)).collect(Collectors.toList());
    }

    @Override
    public List<ConferenceRoomDto> getAllRooms()
    {
        List<ConferenceRoom> rooms=conferenceRoomRepository.findAll();
        List<ConferenceRoomDto> roomDtos=rooms.stream().map(conferenceRoom -> roomConverter.parse(conferenceRoom)).collect(Collectors.toList());
        return roomDtos;
    }

    @Override
    public List<BookingDto> getAllBookings()
    {
        List<Booking> bookings=bookingRepository.findAll();
        List<BookingDto> bookingDtos=bookings.stream().map(booking -> bookingConverter.parse(booking)).collect(Collectors.toList());
        return bookingDtos;
    }

}
