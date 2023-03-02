package com.example.baekboom.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class memberRankDto {

    public memberRankDto(String user_id, Long score, int rank){
        this.user_id =user_id;
        this.score =score;
        this.rank = rank;
    }

    private String user_id;
    private Long score;
    private int rank;

}
