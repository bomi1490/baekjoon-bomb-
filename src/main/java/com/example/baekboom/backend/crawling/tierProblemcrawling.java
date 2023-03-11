package com.example.baekboom.backend.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.baekboom.backend.dao.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class tierProblemcrawling {

    private final memberDao memberDao;
    private final problemDao problemDao;

    public tierProblemcrawling(memberDao memberDao, problemDao problemDao){
        this.memberDao = memberDao;
        this.problemDao = problemDao;
    }

    public Elements crawl_document(String URL){
        Elements elements = new Elements();
        try{
            Document doc = Jsoup.connect(URL).get();
            elements = doc.select("td.list_problem_id");
        } catch(IOException e){
            e.printStackTrace();
        }

        return elements;
    }

    public List<Long> tierProblem(Long tier){
        List<Long> problems = new ArrayList<>();
        int page = 1;

        while(true) {
            String crawlingURL = String.format("https://www.acmicpc.net/problemset?sort=no_asc&tier=%d&page=%d", tier, page);
            Elements elements = crawl_document(crawlingURL);

            if (elements.isEmpty()){ break;}
            elements.eachText().forEach(item -> problems.add(Long.parseLong(item)));
            page++;
        }

        return problems;
    }

    //DB에서 List로 추출하기 : 팀이름으로 DB 검색하여 해당 팀의 회원들이 푼 문제를 가져옴
    public List<Long> get_already_done(String team, Long tier){
        List<Long> problems = new ArrayList<>();
        memberDao.getTeamMember(team).keySet().forEach(item -> problems.addAll(problemDao.get_problems_with_level(item,tier)));
        return null;
    }


    //tier에서 건진 것 중에서 get_already_done 메소드에서 가져온 문제를 제거한 후, random으로 cnt개를 고름
    // 매개변수로 tier와 team이름, 문제 개수가 꼭 있어야 함

    public List<Long> recommend_problems(Long tier, String team, int cnt){
        List<Long> recommend = new ArrayList<>();

        // DB 처리 필요함
        List<Long> already_done = get_already_done(team, tier);
        List<Long> problems = tierProblem(tier);
        problems.remove(already_done);
        Collections.shuffle(problems);

        // 몇개만 추출
        for (int i=0 ; i<cnt ; i++){
            recommend.add(problems.get(i));
        }

        return recommend;

    }

}
