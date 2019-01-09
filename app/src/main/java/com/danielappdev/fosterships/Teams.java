package com.danielappdev.fosterships;

public class Teams {
    String score, teamname;

    public Teams() {
    }

    public Teams(String score, String teamname)
    {
        this.score = score;
        this.teamname = teamname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }
}
