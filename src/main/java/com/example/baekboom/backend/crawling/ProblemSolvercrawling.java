package com.example.baekboom.backend.crawling;

import com.example.baekboom.backend.dao.FCMDao;
import com.example.baekboom.backend.dao.memberDao;
import com.example.baekboom.backend.dao.problemDao;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.ProblemEntity;
import com.example.baekboom.backend.dto.problemDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.baekboom.backend.service.FirebaseService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProblemSolvercrawling {

    private final memberDao memberDao;
    private final problemDao problemDao;
    private final FirebaseService firebaseService;
    private final FCMDao fcmDao;
    @Autowired
    public ProblemSolvercrawling(memberDao memberDao, FirebaseService firebaseService, FCMDao fcmDao, problemDao problemDao){
        this.problemDao = problemDao;
        this.memberDao = memberDao;
        this.firebaseService = firebaseService;
        this.fcmDao = fcmDao;
    }

    public class Tuple<K, V>{
        private K name;
        private V time;

        public Tuple(K name, V time) {
            this.name = name;
            this.time = time;
        }

        public void put(K name, V time){
            this.name = name;
            this.time = time;
        }
        public K getName(){
            return this.name;
        }

        public V getTime() {
            return this.time;
        }
    }

    public List<String> get_team_member(String team){
        List<String> members = new ArrayList<>();
        memberDao.getTeamMember(team).keySet().forEach(item -> members.add(item));
        return members;
    }



    public Elements crawl_document(String URL){
        Elements elements = new Elements();
        try{
            Document doc = Jsoup.connect(URL).get();
            elements = doc.select("table.table.table-striped.table-bordered");
            elements = elements.select("td a");
        } catch(IOException e){
            e.printStackTrace();
        }

        return elements;
    }



    public List<Tuple<String, String>> get_solved_member(List<String> solver_and_time, List<String> members){
        List<Tuple<String, String>> solved_member = new ArrayList<>();
        // 짝수에 있는 사람들이 나타나게 된다.
        for (int i=0;i<11;i+=2){
            String solver = solver_and_time.get(i);
            if (members.contains(solver)){
                Tuple<String, String> tuple = new Tuple<>(solver, solver_and_time.get(i+1));
                solved_member.add(tuple);
            }
        }
        return solved_member;
    }



    public void sleeping(){
        try{
            Thread.sleep(30*1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }



    public void catch_solver(List<Long> problems, List<String> members, String team_code, Long level) {
        List<Tuple<String, String>> solved_member = new ArrayList<>();
        List<String> tokens = fcmDao.getfcmToken(team_code);
        // 문제 하나씩 돌아가면서 돌려야 함

        for (Long problem : problems) {
            String crawlingURL = String.format("https://www.acmicpc.net/problem/status/%s", problem.toString());
            Elements elements = crawl_document(crawlingURL);
            List<String> solver_and_time = elements.eachText();
            solved_member = get_solved_member(solver_and_time, members);
            for (Tuple<String, String> solve : solved_member) {
                if (!problemDao.problem_exist(solve.getName(), problem)) {
                    problemDto problemDto = new problemDto(problem, solve.getName(), level, LocalDateTime.now());
                    problemDao.saveproblem(problemDto);
                    for (String token : tokens) {
                        try {
                            firebaseService.sendMessage(solve.getName(), solve.getTime(), token);
                        } catch (IOException e) {
                        }
                    }
                }
            }
        }
        sleeping();
    }

}
