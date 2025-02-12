package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.request.ReplyRequestDto;
import com.example.examplescheduleapp.dto.response.PageResponseDto;
import com.example.examplescheduleapp.dto.response.ReplyResponseDto;
import com.example.examplescheduleapp.dto.response.ScheduleResponseDto;
import com.example.examplescheduleapp.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/{schedule_id}")
    public ResponseEntity<ReplyResponseDto> save(
            @PathVariable Long schedule_id,
            @RequestBody ReplyRequestDto dto,
            HttpServletRequest request
    ){
        ReplyResponseDto savedReply = replyService.save(dto.getContents(), schedule_id, request);
        return new ResponseEntity<>(savedReply, HttpStatus.CREATED);
    }

    @GetMapping("/{schedule_id}/guest")
    public ResponseEntity<PageResponseDto<ReplyResponseDto>> findByScheduleId(
            @PathVariable Long schedule_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponseDto<ReplyResponseDto> findByScheduleIdReply = replyService.findByScheduleId(schedule_id, page, size);
        return new ResponseEntity<>(findByScheduleIdReply, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReplyResponseDto> update(
            @PathVariable Long id,
            @RequestBody ReplyRequestDto dto,
            HttpServletRequest request
    ){
        ReplyResponseDto updatedReply = replyService.update(id, dto.getContents(), request);
        return new ResponseEntity<>(updatedReply, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        replyService.delete(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
