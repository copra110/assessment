package com.mashreq.assessment.validator;

import com.mashreq.assessment.config.AppConfig;
import com.mashreq.assessment.dto.BookingRequestDTO;
import com.mashreq.assessment.model.ConferenceRoom;
import com.mashreq.assessment.repository.ConferenceRoomRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
public class BookingValidator implements ConstraintValidator<ValidBooking, BookingRequestDTO> {


    /*private static final LocalTime MAINTENANCE_START_1 = LocalTime.of(9, 0);
    private static final LocalTime MAINTENANCE_END_1 = LocalTime.of(9, 15);

    private static final LocalTime MAINTENANCE_START_2 = LocalTime.of(13, 0);
    private static final LocalTime MAINTENANCE_END_2 = LocalTime.of(13, 15);

    private static final LocalTime MAINTENANCE_START_3 = LocalTime.of(17, 0);
    private static final LocalTime MAINTENANCE_END_3 = LocalTime.of(17, 15);*/
    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    @Override
    public void initialize(ValidBooking constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookingRequestDTO booking, ConstraintValidatorContext context) {

        if (booking.getEndTime() == null || booking.getStartTime() == null) {
            addError(context, "startTime", "Start time and end time must be provided");
            return false;
        }

        if (booking.getEndTime().isBefore(booking.getStartTime())) {
            addError(context, "endTime", "End time must be after start time");
            return false;
        }

        /*if (overlapsWithMaintenance(booking.getStartTime(), booking.getEndTime())) {
            addError(context, "startTime", "Booking overlaps with maintenance time");
            return false;
        }*/

        Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());

        if (!isValidInterval(duration)) {
            addError(context, "startTime", "Duration must be in 15-minute intervals");
            return false;
        }

        if (!isValidMaxPeople(booking)) {
            addError(context, "numberOfPeople", "Number of people exceeds the maximum capacity of the room");
            return false;
        }

        return true;
    }

    /*private boolean overlapsWithMaintenance(LocalTime startTime, LocalTime endTime) {
        // Check against each maintenance period
        if (overlapsWithMaintenancePeriod(startTime, endTime, MAINTENANCE_START_1, MAINTENANCE_END_1) ||
                overlapsWithMaintenancePeriod(startTime, endTime, MAINTENANCE_START_2, MAINTENANCE_END_2) ||
                overlapsWithMaintenancePeriod(startTime, endTime, MAINTENANCE_START_3, MAINTENANCE_END_3)) {
            return true;
        }

        return false;
    }*/

    private boolean overlapsWithMaintenancePeriod(LocalTime bookingStart, LocalTime bookingEnd, LocalTime maintenanceStart, LocalTime maintenanceEnd) {
        return bookingStart.isBefore(maintenanceEnd) && bookingEnd.isAfter(maintenanceStart);
    }

    private boolean isValidInterval(Duration duration) {
        return duration.getSeconds() % (appConfig.getBookingIntervalMinutes() * 60) == 0;
    }

    private boolean isValidMaxPeople(BookingRequestDTO booking) {
        return booking.getNumberOfPeople() <= conferenceRoomRepository.findMaxCapacity();
    }



    private void addError(ConstraintValidatorContext context, String fieldName, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }
}
