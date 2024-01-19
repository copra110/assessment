package com.mashreq.assessment.config;

import com.mashreq.assessment.model.ConferenceRoom;
import com.mashreq.assessment.model.MaintenanceTiming;
import com.mashreq.assessment.repository.ConferenceRoomRepository;
import com.mashreq.assessment.repository.MaintenanceTimingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

@Component
public class SampleDataInitializer implements CommandLineRunner {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final MaintenanceTimingRepository maintenanceTimingRepository;

    public SampleDataInitializer(
            ConferenceRoomRepository conferenceRoomRepository,
            MaintenanceTimingRepository maintenanceTimingRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.maintenanceTimingRepository = maintenanceTimingRepository;
    }

    @Override
    public void run(String... args) {
        initializeMaintenanceTimings();
        initializeConferenceRooms();
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
