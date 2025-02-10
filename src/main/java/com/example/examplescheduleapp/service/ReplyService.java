package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.dto.response.ReplyResponseDto;
import com.example.examplescheduleapp.entity.Reply;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.exception.HasNoAuthorizationException;
import com.example.examplescheduleapp.exception.ReplyNotFoundException;
import com.example.examplescheduleapp.exception.ScheduleNotFoundException;
import com.example.examplescheduleapp.exception.UserNotFoundException;
import com.example.examplescheduleapp.repository.ReplyRepository;
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


@Service
@RequiredArgsConstructor
public class ReplyService implements CommonMethods {

    private final ReplyRepository replyRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReplyResponseDto save(String contents, Long schedule_id, HttpServletRequest request) {

        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();

        User findByNicknameUser = getFindByNicknameUserOrElseThrow(nickname);

        Schedule findByIdSchedule = getFindByIdScheduleOrElseThrow(schedule_id);

        Reply reply = new Reply(contents, findByNicknameUser, findByIdSchedule);

        Reply savedReply = replyRepository.save(reply);

        return new ReplyResponseDto(savedReply);
    }

    @Transactional(readOnly = true)
    public Page<ReplyResponseDto> findByScheduleId(Long schedule_id, int page, int size) {

        if (size <= 0) { size = 10; }

        Pageable pageable = PageRequest.of(page, size);

        return replyRepository.findBySchedule_idOrderByUpdatedAtDesc(schedule_id, pageable).map(ReplyResponseDto::toDtoReply);
    }

    @Transactional
    public ReplyResponseDto update(Long id, String contents, HttpServletRequest request) {

        checkAuthorizationOfReply(id, request);

        Reply findByIdReply = getFindByIdReplyOrElseThrow(id);

        compareEqualsAndThrow(contents.equals(findByIdReply.getContents()), HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");

        findByIdReply.setContents(contents);

        Reply savedReply = replyRepository.save(findByIdReply);

        return new ReplyResponseDto(savedReply);
    }

    @Transactional
    public void delete(Long id, HttpServletRequest request) {

        checkAuthorizationOfReply(id, request);

        Reply findByIdReply = getFindByIdReplyOrElseThrow(id);

        replyRepository.delete(findByIdReply);
    }


    private void checkAuthorizationOfReply(Long id, HttpServletRequest request) {
        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        Reply findByIdReply = getFindByIdReplyOrElseThrow(id);
        if (!nickname.equals(findByIdReply.getUser().getNickname())){
            throw new HasNoAuthorizationException();
        }
    }

    private Reply getFindByIdReplyOrElseThrow(Long id) {
        return replyRepository.findById(id).orElseThrow(ReplyNotFoundException::new);
    }

    private User getFindByNicknameUserOrElseThrow(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
    }

    private Schedule getFindByIdScheduleOrElseThrow(Long schedule_id) {
        return scheduleRepository.findById(schedule_id).orElseThrow(ScheduleNotFoundException::new);
    }

}
