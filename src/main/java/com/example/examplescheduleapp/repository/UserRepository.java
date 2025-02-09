package com.example.examplescheduleapp.repository;

import com.example.examplescheduleapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

}
