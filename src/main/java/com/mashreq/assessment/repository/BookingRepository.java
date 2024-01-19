package com.mashreq.assessment.repository;

import com.mashreq.assessment.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE (b.startTime BETWEEN :startTime AND :endTime) OR (b.endTime BETWEEN :startTime AND :endTime)")
    List<Booking> findBookingsInTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
