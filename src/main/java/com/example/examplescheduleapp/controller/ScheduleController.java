package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.request.ScheduleSaveRequestDto;
import com.example.examplescheduleapp.dto.request.ScheduleUpdateRequestDto;
import com.example.examplescheduleapp.dto.response.ScheduleResponseDto;
import com.example.examplescheduleapp.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
            @Valid @RequestBody ScheduleSaveRequestDto dto,
            HttpServletRequest request
    ){
        ScheduleResponseDto savedSchedule = scheduleService.save(request, dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll(){
        List<ScheduleResponseDto> findAllSchedule = scheduleService.findAll();
        return new ResponseEntity<>(findAllSchedule, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(
            @PathVariable Long id
    ){
        ScheduleResponseDto findByIdSchedule = scheduleService.findById(id);
        return new ResponseEntity<>(findByIdSchedule, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequestDto dto,
            HttpServletRequest request
    ){
        ScheduleResponseDto updatedSchedule = scheduleService.update(id, request, dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        scheduleService.delete(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
