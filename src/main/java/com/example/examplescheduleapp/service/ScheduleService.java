package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.dto.response.ScheduleResponseDto;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.exception.HasNoAuthorizationException;
import com.example.examplescheduleapp.exception.ScheduleNotFoundException;
import com.example.examplescheduleapp.exception.UserNotFoundException;
import com.example.examplescheduleapp.repository.ScheduleRepository;
import com.example.examplescheduleapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ScheduleService implements CommonMethods {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    @Transactional
    public ScheduleResponseDto save(HttpServletRequest request, String title, String contents) {

        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();

        User findByNicknameUser = getFindByNicknameUserOrElseThrow(nickname);

        Schedule schedule = new Schedule(findByNicknameUser, title, contents);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> findAll(int page, int size){

        if (size <= 0) { size = 10; }

        Pageable pageable = PageRequest.of(page, size);

        return scheduleRepository.findAllByOrderByUpdatedAtDesc(pageable).map(ScheduleResponseDto::toDtoSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);

        return new ScheduleResponseDto(findByIdSchedule);
    }

    @Transactional
    public ScheduleResponseDto update(Long id, HttpServletRequest request, String title, String contents) {

        checkAuthorizationOfSchedule(id, request);

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);

        compareEqualsAndThrow(isEqualsToDB(title, contents, findByIdSchedule), HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");

        findByIdSchedule.updateTitleAndContents(title, contents);

        Schedule updatedSchedule = scheduleRepository.save(findByIdSchedule);

        return new ScheduleResponseDto(updatedSchedule);
    }

    @Transactional
    public void delete(Long id, HttpServletRequest request) {

        checkAuthorizationOfSchedule(id, request);

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);

        scheduleRepository.delete(findByIdSchedule);
    }


    private void checkAuthorizationOfSchedule(Long id, HttpServletRequest request) {
        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(id);
        if (!nickname.equals(findByIdSchedule.getUser().getNickname())){
            throw new HasNoAuthorizationException();
        }
    }

    private boolean isEqualsToDB(String title, String contents, Schedule findByIdSchedule){
        return title.equals(findByIdSchedule.getTitle()) && contents.equals(findByIdSchedule.getContents());
    }

    private Schedule getFindByIdScheduleOrElseThrow(Long id) {
        return scheduleRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
    }

    private User getFindByNicknameUserOrElseThrow(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
    }

}
