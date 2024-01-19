package com.mashreq.assessment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "conference_room_id", foreignKey=@ForeignKey(name = "Fk_conference_room_booking"))
    private ConferenceRoom room;

    @NotNull(message = "please provide a valid value for meeting Start Time")
    private LocalDateTime startTime;

    @NotNull(message = "please provide a valid value for meeting End Time")
    private LocalDateTime endTime;

    @Min(value = 1, message = "minimum number of people allowed cant be lees thatn one person")
    @Max(value = 20, message = "maximum number of people allowed cant exceed 20 people")
    private int numberOfPeople;

    @NotEmpty(message = "owner name must be present")
    private String owner;
}
