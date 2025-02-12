package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.dto.response.PageResponseDto;
import com.example.examplescheduleapp.dto.response.ReplyResponseDto;
import com.example.examplescheduleapp.entity.Reply;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.entity.User;
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
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ReplyService implements CommonMethods{

    private final ReplyRepository replyRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReplyResponseDto save(String contents, Long schedule_id, HttpServletRequest request) {

        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        User findByNicknameUser = userRepository.findByNickname(nickname).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
        Schedule findByIdSchedule =scheduleRepository.findById(schedule_id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        Reply reply = new Reply(contents, findByNicknameUser, findByIdSchedule);
        Reply savedReply = replyRepository.save(reply);

        Long replyNumber = findByIdSchedule.getReplyNumber();
        findByIdSchedule.setReplyNumber(replyNumber+1);
        scheduleRepository.save(findByIdSchedule);

        return new ReplyResponseDto(savedReply);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<ReplyResponseDto> findByScheduleId(Long schedule_id, int page, int size) {

        if (size <= 0) { size = 10; }
        Pageable pageable = PageRequest.of(page, size);
        Page<ReplyResponseDto> findByScheduleIdReply = replyRepository.findBySchedule_idOrderByUpdatedAtDesc(schedule_id, pageable).map(ReplyResponseDto::toDtoReply);

        return new PageResponseDto<ReplyResponseDto>(findByScheduleIdReply);
    }

    @Transactional
    public ReplyResponseDto update(Long id, String contents, HttpServletRequest request) {

        checkAuthorizationAtReply(id, request);

        Reply findByIdReply = replyRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
        ifTrueThenThrow(contents.equals(findByIdReply.getContents()), HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");

        findByIdReply.setContents(contents);
        Reply savedReply = replyRepository.save(findByIdReply);
        return new ReplyResponseDto(savedReply);
    }

    @Transactional
    public void delete(Long id, HttpServletRequest request) {

        checkAuthorizationAtReply(id, request);

        Reply findByIdReply = replyRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        Long replyNumber = findByIdReply.getSchedule().getReplyNumber();
        findByIdReply.getSchedule().setReplyNumber(replyNumber+1);
        scheduleRepository.save(findByIdReply.getSchedule());

        replyRepository.delete(findByIdReply);
    }


    private void checkAuthorizationAtReply(Long id, HttpServletRequest request) {
        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        Reply findByIdReply = replyRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
        ifTrueThenThrow(!nickname.equals(findByIdReply.getUser().getNickname()), HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
    }

}
