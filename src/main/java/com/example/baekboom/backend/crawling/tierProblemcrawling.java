package com.example.baekboom.backend.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.baekboom.backend.dao.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class tierProblemcrawling {

    private final memberDao memberDao;
    private final problemDao problemDao;

    public tierProblemcrawling(memberDao memberDao, problemDao problemDao){
        this.memberDao = memberDao;
        this.problemDao = problemDao;
    }

    public List<Elements> crawl_document(String URL){
        List<Elements> elements = new ArrayList<>();
        Elements elements1 = new Elements();
        Elements elements2 = new Elements();
        try{
            Document doc = Jsoup.connect(URL).get();
            elements1 = doc.select("td.list_problem_id");
            elements2 = doc.select("tr td:nth-child(2) a");
            elements.add(elements1);
            elements.add(elements2);
        } catch (IOException e){
            e.printStackTrace();
        }
        return elements;

    }
//    public Elements crawl_document(String URL){
//        Elements elements = new Elements();
//        Elements elements2 = new Elements();
//        try{
//            Document doc = Jsoup.connect(URL).get();
//            elements = doc.select("td.list_problem_id");
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//        return elements;
//    }


    public Map<Long, String> tierProblem(Long tier){
        Map<Long, String> problems = new HashMap<>();
        int page = 1;

        while(true) {
            String crawlingURL = String.format("https://www.acmicpc.net/problemset?sort=no_asc&tier=%d&page=%d", tier, page);
            List<Elements> elements = crawl_document(crawlingURL);

            if (elements.isEmpty()){ break;}
            for (int i =0; i == elements.get(0).size(); i++){
                problems.put(Long.parseLong(elements.get(0).get(i).text()), elements.get(1).get(i).text());
            }
            page++;
        }
        return problems;
    }

    //DB?????? List??? ???????????? : ??????????????? DB ???????????? ?????? ?????? ???????????? ??? ????????? ?????????
    public List<Long> get_already_done(String team, Long tier){
        List<Long> problems = new ArrayList<>();
        memberDao.getTeamMember(team).keySet().forEach(item -> problems.addAll(problemDao.get_problems_with_level(item,tier)));
        return null;
    }


    //tier?????? ?????? ??? ????????? get_already_done ??????????????? ????????? ????????? ????????? ???, random?????? cnt?????? ??????
    // ??????????????? tier??? team??????, ?????? ????????? ??? ????????? ???

    public Map<Long, String> recommend_problems(Long tier, String team, int cnt){
        Map<Long, String> recommend = new HashMap<>();

        // DB ?????? ?????????
        List<Long> already_done = get_already_done(team, tier);
        Map<Long, String> problems= tierProblem(tier);
        problems.remove(already_done);
        List<Long> numbers = problems.keySet().stream().toList();
        Collections.shuffle(numbers);

        // ????????? ??????
        for (int i=0 ; i<cnt ; i++){
            recommend.put(numbers.get(i), problems.get(numbers.get(i)));
        }

        return recommend;

    }

}
