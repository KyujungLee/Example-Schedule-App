package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.response.ReplyResponseDto;
import com.example.examplescheduleapp.entity.Reply;
import com.example.examplescheduleapp.entity.Schedule;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.repository.ReplyRepository;
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
public class ReplyService implements Check{

    private final ReplyRepository replyRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReplyResponseDto save(String contents, Long schedule_id, String nickname, HttpServletRequest request) {

        checkAuthorization(nickname, request);

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        Schedule findByIdSchedule = scheduleRepository.findById(schedule_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));

        Reply reply = new Reply(contents, findByNicknameUser, findByIdSchedule);

        Reply savedReply = replyRepository.save(reply);

        return new ReplyResponseDto(savedReply);
    }

    @Transactional(readOnly = true)
    public List<ReplyResponseDto> findByScheduleId(Long schedule_id) {

        List<Reply> findByScheduleIdReply = replyRepository.findBySchedule_id(schedule_id);

        return findByScheduleIdReply.stream().map(ReplyResponseDto::toDtoReply).toList();
    }


    @Transactional
    public ReplyResponseDto update(Long id, String contents, String nickname, HttpServletRequest request) {

        checkAuthorization(nickname, request);

        Reply findByIdReply = replyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));


        if (contents.equals(findByIdReply.getContents())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");
        }

        findByIdReply.setContents(contents);

        Reply savedReply = replyRepository.save(findByIdReply);

        return new ReplyResponseDto(savedReply);
    }

    @Transactional
    public void delete(Long id, String nickname, HttpServletRequest request) {

        checkAuthorization(nickname, request);

        Reply findByIdReply = replyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));

        replyRepository.delete(findByIdReply);
    }

}
