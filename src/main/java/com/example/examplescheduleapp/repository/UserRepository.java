package com.example.examplescheduleapp.repository;

import com.example.examplescheduleapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
