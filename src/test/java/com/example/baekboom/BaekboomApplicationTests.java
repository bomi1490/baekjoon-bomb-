package com.example.baekboom;

import com.example.baekboom.backend.crawling.ProblemSolvercrawling;
import com.example.baekboom.backend.dao.memberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaekboomApplicationTests {

	@Autowired
	private com.example.baekboom.backend.repository.memberRepository memberRepository;
	private com.example.baekboom.backend.repository.teamRepository teamRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void text(){

		//tierProblemcrawling t = new tierProblemcrawling(new memberDao(memberRepository, teamRepository));
		//List<String> K = t.recommend_problems(10, "dsbhdbhfj", 4);
		//System.out.println(K);
	}

	@Test
	void text1(){
		//memberDao memberDao = new memberDao(memberRepository, teamRepository);
		//ProblemSolvercrawling p = new ProblemSolvercrawling(memberDao);
		//ProblemSolvercrawling.Tuple pu = p.catch_solver("2468", "11234");
		//System.out.println(pu.getName());
		//System.out.println(pu.getTime());
	}

}
