package com.example.baekboom.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemSolvercrawling {

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

        return null;
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
            Thread.sleep(60*1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }



    public Tuple<String, String> catch_solver(String problem, String team){
        Tuple<String, String> solved_member = new Tuple<>();
        List<String> members = new ArrayList<>();
        members.add("jw4711");
        while(true){
            String crawlingURL = String.format("https://www.acmicpc.net/problem/status/%s", problem);
            Elements elements = crawl_document(crawlingURL);

            List<String> solver_and_time = elements.eachText();
            solved_member = get_solved_member(solver_and_time, members);

            if (!solved_member.getName().isEmpty()){ break;}
            sleeping();
        }

        return solved_member;
    }

}
