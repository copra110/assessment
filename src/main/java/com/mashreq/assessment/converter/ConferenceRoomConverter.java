package com.mashreq.assessment.converter;

import com.mashreq.assessment.dto.ConferenceRoomDto;
import com.mashreq.assessment.model.ConferenceRoom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class ConferenceRoomConverter implements Converter<ConferenceRoomDto, ConferenceRoom>
{
    @Autowired
    private BookingConverter bookingConverter;

    @Override
    public ConferenceRoom convert(ConferenceRoomDto conferenceRoomDto)
    {
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        BeanUtils.copyProperties(conferenceRoomDto, conferenceRoom);
        conferenceRoom.setBookings(conferenceRoomDto.getBookings().stream().map(bookingDto -> bookingConverter.convert(bookingDto)).collect(Collectors.toSet()));
        return conferenceRoom;
    }

    public ConferenceRoomDto parse(ConferenceRoom conferenceRoom)
    {
        ConferenceRoomDto conferenceRoomDto = new ConferenceRoomDto();
        BeanUtils.copyProperties(conferenceRoom, conferenceRoomDto);
        conferenceRoomDto.setBookings(conferenceRoom.getBookings().stream().map(booking -> bookingConverter.parse(booking)).collect(Collectors.toList()));
        return conferenceRoomDto;
    }
}
