package com.mashreq.assessment.repository;

import com.mashreq.assessment.model.MaintenanceTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface MaintenanceTimingRepository extends JpaRepository<MaintenanceTiming, Long> {

    @Query("SELECT mt FROM MaintenanceTiming mt WHERE mt.startTime = :startTime AND mt.endTime = :endTime")
    Optional<MaintenanceTiming> findByStartTimeAndEndTime(
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}
