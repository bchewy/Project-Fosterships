package com.danielappdev.fosterships;

public class Teams {
    String teamname;
    Long score;

    public Teams() {
    }

    public Teams(Long score, String teamname)
    {
        this.score = score;
        this.teamname = teamname;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }
}
