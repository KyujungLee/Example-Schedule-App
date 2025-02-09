package com.example.examplescheduleapp.repository;

import com.example.examplescheduleapp.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findBySchedule_id(Long schedule_id);
}
