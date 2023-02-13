package com.example.baekboom.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class problemDto {

    public problemDto(Long problem, String user_id, Long level, LocalDateTime event_time){
        this.problem = problem;
        this.user_id = user_id;
        this.level = level;
        this.event_time = event_time;

    }

    private Long problem;
    private Long level;
    private LocalDateTime event_time;
    private String user_id;
}
