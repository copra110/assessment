package com.mashreq.assessment.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<Booking> bookings;

    @ManyToMany
    @JoinTable(
            name = "conference_room_maintenance_timing",
            joinColumns = @JoinColumn(name = "conference_room_id"),
            inverseJoinColumns = @JoinColumn(name = "maintenance_timing_id"))
    private Set<MaintenanceTiming> maintenanceTimings;


    public ConferenceRoom(String name, int capacity)
    {
        this.capacity=capacity;
        this.name= name;
        this.maintenanceTimings=new HashSet<>();
    }
}
