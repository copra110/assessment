package com.mashreq.assessment.repository;

import com.mashreq.assessment.model.ConferenceRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {

    @Query(nativeQuery = true, value = """
            SELECT cr.*
               FROM conference_room cr
               WHERE cr.capacity >= :numberOfPeople
                 AND cr.id NOT IN (
                   SELECT DISTINCT cr.id
                   FROM conference_room cr
                   LEFT JOIN conference_room_maintenance_timing crmt ON cr.id = crmt.conference_room_id
                   LEFT JOIN maintenance_timing mt ON crmt.maintenance_timing_id = mt.id
                   WHERE (
                     (mt.start_time < :endTime AND mt.end_time > :startTime)
                     OR (mt.start_time < :startTime AND mt.end_time > :endTime)
                   )
                 )
                 AND cr.id NOT IN (
                   SELECT DISTINCT cr.id
                   FROM conference_room cr
                   LEFT JOIN booking b ON cr.id = b.conference_room_id
                   WHERE (
                     (b.start_time < :bookingEndTime AND b.end_time > :bookingStartTime)
                     OR (b.start_time < :bookingStartTime AND b.end_time > :bookingEndTime)
                   )
                 )
            """)
    List<ConferenceRoom> findFirstAvailableRoomOrderedByCapacity(
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("bookingStartTime") LocalDateTime bookingStartTime,
            @Param("bookingEndTime") LocalDateTime bookingEndTime,
            @Param("numberOfPeople") int numberOfPeople);

    @Query("SELECT MAX(c.capacity) FROM ConferenceRoom c")
    Integer findMaxCapacity();


}
