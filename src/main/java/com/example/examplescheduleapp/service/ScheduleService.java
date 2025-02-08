package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.response.ScheduleResponseDto;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.exception.UserNotFoundException;
import com.example.examplescheduleapp.repository.ScheduleRepository;
import com.example.examplescheduleapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    private final UserRepository userRepository;


    @Transactional
    public ScheduleResponseDto save(HttpServletRequest request, String title, String contents) {

        HttpSession session = request.getSession(false);
        String nickname = session.getAttribute(Const.LOGIN_USER).toString();

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new UserNotFoundException("존재하지 않는 유저입니다."));

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

        Schedule findByIdSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(findByIdSchedule);
    }


    @Transactional
    public ScheduleResponseDto update(Long id, HttpServletRequest request, String title, String contents) {

        Schedule findByIdSchedule = getSchedule(id, request);

        findByIdSchedule.updateTitleAndContents(title, contents);

        Schedule updatedSchedule = scheduleRepository.save(findByIdSchedule);

        return new ScheduleResponseDto(updatedSchedule);
    }

    @Transactional(readOnly = true)
    public void delete(Long id, HttpServletRequest request) {

        Schedule findByIdSchedule = getSchedule(id, request);

        scheduleRepository.delete(findByIdSchedule);
    }

    private Schedule getSchedule(Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String nickname = session.getAttribute(Const.LOGIN_USER).toString();

        Schedule findByIdSchedule = scheduleRepository.findByIdOrElseThrow(id);

        if (!findByIdSchedule.getUser().getNickname().equals(nickname)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다.");
        }

        return findByIdSchedule;
    }

}
