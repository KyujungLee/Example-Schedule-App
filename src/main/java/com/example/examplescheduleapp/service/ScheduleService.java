package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.response.ScheduleResponseDto;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.repository.ScheduleRepository;
import com.example.examplescheduleapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleService implements Check{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    @Transactional
    public ScheduleResponseDto save(String nickname, HttpServletRequest request, String title, String contents) {

        checkAuthorization(nickname, request);

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        Schedule schedule = new Schedule(findByNicknameUser, title, contents);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll(){

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDtoSchedule).toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);

        return new ScheduleResponseDto(findByIdSchedule);
    }


    @Transactional
    public ScheduleResponseDto update(Long id, String nickname, HttpServletRequest request, String title, String contents) {

        checkAuthorization(nickname, request);

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);

        findByIdSchedule.updateTitleAndContents(title, contents);

        Schedule updatedSchedule = scheduleRepository.save(findByIdSchedule);

        return new ScheduleResponseDto(updatedSchedule);
    }

    @Transactional
    public void delete(Long id, String nickname, HttpServletRequest request) {

        checkAuthorization(nickname, request);

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);

        scheduleRepository.delete(findByIdSchedule);
    }

    private Schedule getFindByIdScheduleOrElseThrow(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"일정이 존재하지 않습니다"));
    }

}
