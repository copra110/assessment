package com.mashreq.assessment.dto;

import com.mashreq.assessment.validator.ValidBooking;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidBooking
public class BookingRequestDTO {

    @Future(message = "start time cant be in the past please choose a future time")
    @NotNull(message = "please provide a valid value for meeting Start Time")
    private LocalTime startTime;

    @NotNull(message = "please provide a valid value for meeting End Time")
    private LocalTime endTime;

    @Min(value = 1, message = "minimum number of people allowed cant be lees thatn one person")
    @Max(value = 20, message = "maximum number of people allowed cant exceed 20 people")
    private int numberOfPeople;

    @NotEmpty(message = "owner name must be present")
    private String owner;
}
