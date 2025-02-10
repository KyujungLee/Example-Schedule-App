package com.example.examplescheduleapp.repository;

import com.example.examplescheduleapp.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findBySchedule_idOrderByUpdatedAtDesc(Long scheduleId, Pageable pageable);
}
