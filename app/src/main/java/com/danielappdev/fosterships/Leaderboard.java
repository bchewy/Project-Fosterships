package com.danielappdev.fosterships;

public class Leaderboard {
    private int Ranking;
    private String GroupName;
    private int Score;


    public Leaderboard(){

    }
    public Leaderboard(int Ranking, String GroupName, int Score){
        this.Ranking=Ranking;
        this.GroupName=GroupName;
        this.Score=Score;
    }

    public int Ranking() {
        return Ranking;
    }
    public String GroupName() {
        return GroupName;
    }
    public  int Score(){
        return Score;
    }

    public void setRanking(int ranking) {
        this.Ranking = ranking;
    }

    public void setGroupName(String groupname) {
        this.GroupName = groupname;

    }

    public void setScore(int score) {
        this.Score = score;
    }


}
