package com.example.baekboom.backend.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class tierProblemcrawling {

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

    public List<String> tierProblem(int tier){
        List<String> problems = new ArrayList<>();
        int page = 1;

        while(true) {
            String crawlingURL = String.format("https://www.acmicpc.net/problemset?sort=no_asc&tier=%d&page=%d", tier, page);
            Elements elements = crawl_document(crawlingURL);

            if (elements.isEmpty()){ break;}
            problems.addAll(elements.eachText());
            page++;
        }

        return problems;
    }

    //DB에서 List로 추출하기 : 팀이름으로 DB 검색하여 해당 팀의 회원들이 푼 문제를 가져옴
    public List<String> get_already_done(String team){

        return null;
    }


    //tier에서 건진 것 중에서 get_already_done 메소드에서 가져온 문제를 제거한 후, random으로 cnt개를 고름
    // 매개변수로 tier와 team이름, 문제 개수가 꼭 있어야 함
    public List<String> recommend_problems(int tier, String team, int cnt){
        List<String> recommend = new ArrayList<>();

        // DB 처리 필요함
        List<String> already_done = get_already_done(team);
        List<String> problems = tierProblem(tier);
        problems.remove(already_done);
        Collections.shuffle(problems);

        for (int i=0 ; i<cnt ; i++){
            recommend.add(problems.get(i));
        }

        return recommend;

    }
}
