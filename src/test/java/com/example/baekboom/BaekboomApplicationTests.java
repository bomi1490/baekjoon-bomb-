package com.example.baekboom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.baekboom.crawling.*;

import java.util.List;

@SpringBootTest
class BaekboomApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void text(){
		tierProblemcrawling t = new tierProblemcrawling();
		List<String> K = t.recommend_problems(10, "dsbhdbhfj", 4);
		System.out.println(K);
	}

	@Test
	void text1(){
		ProblemSolvercrawling p = new ProblemSolvercrawling();
		ProblemSolvercrawling.Tuple pu = p.catch_solver("2468", "11234");
		System.out.println(pu.getName());
		System.out.println(pu.getTime());
	}

}
