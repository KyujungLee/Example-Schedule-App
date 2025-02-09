package com.example.examplescheduleapp.repository;

import com.example.examplescheduleapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
