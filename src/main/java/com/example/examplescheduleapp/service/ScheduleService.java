package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.ScheduleResponseDto;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    public ScheduleResponseDto save(String username, String title, String contents) {

        Schedule schedule = new Schedule(username, title, contents);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreated_at(),
                savedSchedule.getUpdated_at()
        );

    }

    public List<ScheduleResponseDto> findAll(){

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();

    }

    public ScheduleResponseDto findById(Long id) {

        Schedule findByIdSchedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"아이디가 존재하지 않습니다"));

        return new ScheduleResponseDto(
                findByIdSchedule.getId(),
                findByIdSchedule.getUsername(),
                findByIdSchedule.getTitle(),
                findByIdSchedule.getContents(),
                findByIdSchedule.getCreated_at(),
                findByIdSchedule.getUpdated_at()
        );

    }


    @Transactional
    public ScheduleResponseDto update(Long id, String username, String title, String contents) {

        Schedule findByIdSchedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다"));

        findByIdSchedule.updateUsernameAndTitleAndContents(username, title, contents);

        Schedule updatedSchedule = scheduleRepository.save(findByIdSchedule);

        return new ScheduleResponseDto(
                updatedSchedule.getId(),
                updatedSchedule.getUsername(),
                updatedSchedule.getTitle(),
                updatedSchedule.getContents(),
                updatedSchedule.getCreated_at(),
                updatedSchedule.getUpdated_at()
        );
    }

    public void delete(Long id) {

        Schedule findByIdSchedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다"));

        scheduleRepository.delete(findByIdSchedule);

    }
}
