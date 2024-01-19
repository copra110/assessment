package com.mashreq.assessment.converter;

import com.mashreq.assessment.model.Booking;
import com.mashreq.assessment.model.BookingDto;
import com.mashreq.assessment.model.ConferenceRoom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BookingConverter implements Converter<BookingDto, Booking>
{

    @Override
    public Booking convert(BookingDto bookingDto)
    {
        Booking booking = new Booking();
        BeanUtils.copyProperties(bookingDto, booking);
        return booking;
    }

    public BookingDto parse(Booking booking)
    {
        BookingDto bookingDto = new BookingDto();
        BeanUtils.copyProperties(booking, bookingDto);
        bookingDto.setRoom(booking.getRoom().getName());
        return bookingDto;
    }
}
