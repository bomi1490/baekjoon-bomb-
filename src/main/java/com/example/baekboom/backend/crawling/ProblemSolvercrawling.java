package com.example.baekboom.backend.crawling;

import com.example.baekboom.backend.dao.FCMDao;
import com.example.baekboom.backend.dao.memberDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.baekboom.backend.service.FirebaseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemSolvercrawling {

    private final memberDao memberDao;
    private final FirebaseService firebaseService;
    private final FCMDao fcmDao;
    @Autowired
    public ProblemSolvercrawling(memberDao memberDao, FirebaseService firebaseService, FCMDao fcmDao){
        this.memberDao = memberDao;
        this.firebaseService = firebaseService;
        this.fcmDao = fcmDao;
    }

    public class Tuple<K, V>{
        private K name;
        private V time;

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



    public Tuple<String, String> get_solved_member(List<String> solver_and_time, List<String> members){
        Tuple<String, String> solved_member = new Tuple<>();
        for (int i=0;i<11;i+=2){
            String solver = solver_and_time.get(i);
            if (members.contains(solver)){
                solved_member.put(solver, solver_and_time.get(i+1));
                break;
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



    public Tuple<String, String> catch_solver(List<Long> problems, String team) throws IOException{
        Tuple<String, String> solved_member = new Tuple<>();
        List<String> members = get_team_member(team);
                // 문제 하나씩 돌아가면서 돌려야 함
        while(Boolean.TRUE){
            Boolean Flag = Boolean.TRUE;
            for(Long problem : problems){
                String crawlingURL = String.format("https://www.acmicpc.net/problem/status/%s", problem.toString());
                Elements elements = crawl_document(crawlingURL);
                List<String> solver_and_time = elements.eachText();
                solved_member = get_solved_member(solver_and_time, members);

                if (!solved_member.getName().isEmpty()){
                    Flag = Boolean.FALSE;
                    break;}
                sleeping();
            }
            if (Flag == Boolean.FALSE){
                break;
            }

        }
        List<String> tokens = fcmDao.getfcmToken(team);
        // 푸쉬 알림 기능 넣기
        for (String token : tokens){
            firebaseService.sendMessage(solved_member.getName(), solved_member.getTime(), token);
        }

        return solved_member;
    }

}
