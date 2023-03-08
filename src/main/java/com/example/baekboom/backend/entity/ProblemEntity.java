package com.example.baekboom.backend.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "problem")
public class ProblemEntity {

    @Id
    @Column(name = "problem")
    private Long problem;

    @Column(name = "level")
    private Long level;

    @Column(name = "event_time")
    private LocalDateTime event_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity user;
}
