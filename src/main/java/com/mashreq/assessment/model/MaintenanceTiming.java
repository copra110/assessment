package com.mashreq.assessment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceTiming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(columnDefinition = "TIME")
    private LocalTime endTime;

    @ManyToMany(mappedBy = "maintenanceTimings")
    private List<ConferenceRoom> conferenceRooms;

    public MaintenanceTiming(LocalTime startTime, LocalTime endTime)
    {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
