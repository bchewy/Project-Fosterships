package com.danielappdev.fosterships;

public class Teams {
    String TeamName;
    Long Score;

    public Teams() {
    }

    public Teams(Long Score, String TeamName)
    {
        this.Score = Score;
        this.TeamName = TeamName;
    }

    public Long getScore() {
        return Score;
    }

    public void setScore(Long Score) {
        this.Score = Score;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String TeamName) {
        this.TeamName = TeamName;
    }
}
