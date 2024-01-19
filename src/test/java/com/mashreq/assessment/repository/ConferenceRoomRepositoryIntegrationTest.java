package com.mashreq.assessment.repository;

import com.mashreq.assessment.model.Booking;
import com.mashreq.assessment.model.ConferenceRoom;
import com.mashreq.assessment.model.MaintenanceTiming;
import com.mashreq.assessment.repository.ConferenceRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ConferenceRoomRepositoryIntegrationTest {

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    private MaintenanceTimingRepository maintenanceTimingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        initializeMaintenanceTimings();
        initializeConferenceRooms();
    }

    @Test
    void setupTest() {
        // Your test logic here
        List<ConferenceRoom> rooms = conferenceRoomRepository.findAll();
        List<MaintenanceTiming> timings = maintenanceTimingRepository.findAll();

        // Your assertions here
        assertEquals(4, rooms.size());
        assertEquals(3, timings.size());
    }

    @Test
    void firstComeFirstServeBooking() {
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 1, 12, 0);

        Booking booking1 = Booking.builder()
                .startTime(startTime)
                .endTime(endTime)
                .room(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                                startTime.toLocalTime(),
                                endTime.toLocalTime(),
                                startTime,
                                endTime,
                                10) .get(0))
                .numberOfPeople(10)
                .owner("User1")
                .build();
        booking1 = bookingRepository.save(booking1);

        Booking booking2 = Booking.builder()
                .startTime(startTime)
                .endTime(endTime)
                .room(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                        startTime.toLocalTime(),
                        endTime.toLocalTime(),
                        startTime,
                        endTime,
                        5) .get(0))
                .numberOfPeople(5)
                .owner("User2")
                .build();
        booking2 = bookingRepository.save(booking2);

        Booking booking3 = Booking.builder()
                .startTime(startTime)
                .endTime(endTime)
                .room(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                        startTime.toLocalTime(),
                        endTime.toLocalTime(),
                        startTime,
                        endTime,
                        3) .get(0))
                .numberOfPeople(3)
                .owner("User3")
                .build();
        booking3 = bookingRepository.save(booking3);


        Booking booking4 = Booking.builder()
                .startTime(startTime)
                .endTime(endTime)
                .room(conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                        startTime.toLocalTime(),
                        endTime.toLocalTime(),
                        startTime,
                        endTime,
                        3) .get(0))
                .numberOfPeople(3)
                .owner("User4")
                .build();
        booking4 = bookingRepository.save(booking4);


        assertEquals("Inspire", booking1.getRoom().getName());
        assertEquals("Beauty", booking2.getRoom().getName());
        assertEquals("Amaze", booking3.getRoom().getName());
        assertEquals("Strive", booking4.getRoom().getName());


    }

    @Test
    void findFirstAvailableRoomOrderedByCapacity_EmptyBookings_sizeChecks_sameDate() {

        List<ConferenceRoom> availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                2);

        assertEquals(4, availableRooms.size());
        assertEquals(availableRooms.get(0).getName(),"Amaze");
        assertEquals(availableRooms.get(1).getName(),"Beauty");
        assertEquals(availableRooms.get(2).getName(),"Inspire");
        assertEquals(availableRooms.get(3).getName(),"Strive");


        availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                4);

        assertEquals(3, availableRooms.size());
        assertEquals(availableRooms.get(0).getName(),"Beauty");
        assertEquals(availableRooms.get(1).getName(),"Inspire");
        assertEquals(availableRooms.get(2).getName(),"Strive");

        availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                8);

        assertEquals(2, availableRooms.size());
        assertEquals(availableRooms.get(0).getName(),"Inspire");
        assertEquals(availableRooms.get(1).getName(),"Strive");

        availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                13);

        assertEquals(1, availableRooms.size());
        assertEquals(availableRooms.get(0).getName(),"Strive");
    }

    @Test
    void findMaxCapacity() {
        Integer maxCapacity = conferenceRoomRepository.findMaxCapacity();

        assertEquals(20, maxCapacity);
    }

    @Test
    void optimalBookingRoomSelection() {
        List<ConferenceRoom> availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                5);

        assertEquals("Beauty", availableRooms.get(0).getName());


        availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                15);

        assertEquals("Strive", availableRooms.get(0).getName());
    }

    @Test
    void bookingNotAllowedDuringMaintenance() {
        List<MaintenanceTiming> maintenanceTimings= maintenanceTimingRepository.findAll();
        maintenanceTimings.stream().forEach(maintenanceTiming ->
        {
            List<ConferenceRoom> availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                    LocalTime.of(maintenanceTiming.getStartTime().minusHours(1).getHour(), 0),
                    LocalTime.of(maintenanceTiming.getEndTime().getHour(), maintenanceTiming.getEndTime().getMinute()),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(maintenanceTiming.getStartTime().minusHours(1).getHour(), 0)),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(maintenanceTiming.getEndTime().getHour(), maintenanceTiming.getEndTime().getMinute())),
                    5);
            assertEquals(0, availableRooms.size());
        });
    }

    @Test
    void numberOfPeopleExceedsMaxCapacity() {
        List<ConferenceRoom> availableRooms = conferenceRoomRepository.findFirstAvailableRoomOrderedByCapacity(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                25);

        assertEquals(0, availableRooms.size());
    }

    private void initializeMaintenanceTimings() {
        MaintenanceTiming maintenanceTiming1 = new MaintenanceTiming(LocalTime.of(9, 0), LocalTime.of(9, 15));
        MaintenanceTiming maintenanceTiming2 = new MaintenanceTiming(LocalTime.of(13, 0), LocalTime.of(13, 15));
        MaintenanceTiming maintenanceTiming3 = new MaintenanceTiming(LocalTime.of(17, 0), LocalTime.of(17, 15));

        maintenanceTimingRepository.saveAll(Arrays.asList(maintenanceTiming1, maintenanceTiming2, maintenanceTiming3));
    }

    private void initializeConferenceRooms() {
        ConferenceRoom conferenceRoom1 = new ConferenceRoom("Amaze", 3);
        ConferenceRoom conferenceRoom2 = new ConferenceRoom("Beauty", 7);
        ConferenceRoom conferenceRoom3 = new ConferenceRoom("Inspire", 12);
        ConferenceRoom conferenceRoom4 = new ConferenceRoom("Strive", 20);

        MaintenanceTiming maintenanceTiming1 = maintenanceTimingRepository.findByStartTimeAndEndTime(
                LocalTime.of(9, 0), LocalTime.of(9, 15)).orElse(null);
        MaintenanceTiming maintenanceTiming2 = maintenanceTimingRepository.findByStartTimeAndEndTime(
                LocalTime.of(13, 0), LocalTime.of(13, 15)).orElse(null);
        MaintenanceTiming maintenanceTiming3 = maintenanceTimingRepository.findByStartTimeAndEndTime(
                LocalTime.of(17, 0), LocalTime.of(17, 15)).orElse(null);

        if (maintenanceTiming1 != null)
        {
            conferenceRoom1.getMaintenanceTimings().add(maintenanceTiming1);
            conferenceRoom2.getMaintenanceTimings().add(maintenanceTiming1);
            conferenceRoom3.getMaintenanceTimings().add(maintenanceTiming1);
            conferenceRoom4.getMaintenanceTimings().add(maintenanceTiming1);
        }
        if (maintenanceTiming2 != null)
        {
            conferenceRoom1.getMaintenanceTimings().add(maintenanceTiming2);
            conferenceRoom2.getMaintenanceTimings().add(maintenanceTiming2);
            conferenceRoom3.getMaintenanceTimings().add(maintenanceTiming2);
            conferenceRoom4.getMaintenanceTimings().add(maintenanceTiming2);
        }
        if (maintenanceTiming3 != null)
        {
            conferenceRoom1.getMaintenanceTimings().add(maintenanceTiming3);
            conferenceRoom2.getMaintenanceTimings().add(maintenanceTiming3);
            conferenceRoom3.getMaintenanceTimings().add(maintenanceTiming3);
            conferenceRoom4.getMaintenanceTimings().add(maintenanceTiming3);
        }
        conferenceRoomRepository.saveAll(Arrays.asList(conferenceRoom1, conferenceRoom2, conferenceRoom3, conferenceRoom4));
    }




}
