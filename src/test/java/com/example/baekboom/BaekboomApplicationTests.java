package com.example.baekboom;

import com.example.baekboom.backend.crawling.ProblemSolvercrawling;
import com.example.baekboom.backend.crawling.tierProblemcrawling;
import com.example.baekboom.backend.dao.memberDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class BaekboomApplicationTests {

	@Autowired
	private com.example.baekboom.backend.repository.memberRepository memberRepository;
	private com.example.baekboom.backend.repository.teamRepository teamRepository;
	private tierProblemcrawling tierProblemcrawling;

	@Test
	void context(){
		String URL = "https://www.acmicpc.net/problemset?sort=no_asc&tier=3&page=5";
		Elements elements1 = new Elements();
		Elements elements2 = new Elements();
		try{
			Document doc = Jsoup.connect(URL).get();
			elements1 = doc.select("td.list_problem_id");
			elements2 = doc.select("tr td:nth-child(2) a");
			System.out.println(elements1);
			System.out.println(elements2);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

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
