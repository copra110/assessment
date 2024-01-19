package com.mashreq.assessment.dto;

import com.mashreq.assessment.model.Booking;
import com.mashreq.assessment.model.BookingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomDto
{
    private Long id;
    private String name;
    private int capacity;
    private List<BookingDto> bookings;
}
