package com.example.baekboom.backend.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class memberDto {

    public memberDto(String user_id, Long score, Boolean bomb_yn, String team_code){
        this.user_id =user_id;
        this.score =score;
        this.bomb_yn = bomb_yn;
        this.team_code =team_code;
    }

    private String user_id;
    private Long score;
    private Boolean bomb_yn;
    private String team_code;

//    public void setIsBomb(boolean bomb_yn) {
//        this.bomb_yn = bomb_yn;
//    }
}
