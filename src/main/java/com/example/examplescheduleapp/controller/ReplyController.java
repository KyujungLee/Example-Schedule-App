package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.request.ReplyRequestDto;
import com.example.examplescheduleapp.dto.response.ReplyResponseDto;
import com.example.examplescheduleapp.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/{schedule_id}")
    public ResponseEntity<ReplyResponseDto> save(
            @PathVariable Long schedule_id,
            @RequestParam String nickname,
            @RequestBody ReplyRequestDto dto,
            HttpServletRequest request
    ){
        ReplyResponseDto savedReply = replyService.save(dto.getContents(), schedule_id, nickname, request);
        return new ResponseEntity<>(savedReply, HttpStatus.CREATED);
    }

    @GetMapping("/{schedule_id}")
    public ResponseEntity<List<ReplyResponseDto>> findByScheduleId(
            @PathVariable Long schedule_id
    ){
        List<ReplyResponseDto> findByScheduleIdReply = replyService.findByScheduleId(schedule_id);
        return new ResponseEntity<>(findByScheduleIdReply, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReplyResponseDto> update(
            @PathVariable Long id,
            @RequestParam String nickname,
            @RequestBody ReplyRequestDto dto,
            HttpServletRequest request
    ){
        ReplyResponseDto updatedReply = replyService.update(id, dto.getContents(), nickname, request);
        return new ResponseEntity<>(updatedReply, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam String nickname,
            HttpServletRequest request
    ){
        replyService.delete(id, nickname, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
