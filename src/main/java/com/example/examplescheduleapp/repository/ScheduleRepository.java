package com.example.examplescheduleapp.repository;

import com.example.examplescheduleapp.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
