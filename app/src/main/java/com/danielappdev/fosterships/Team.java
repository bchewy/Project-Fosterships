package com.danielappdev.fosterships;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Team {
    private String teamName;
    private int NoOfAuths;
    private int phase1Answers;

    public Team(String teamName, int noOfAuths, int phase1Answers) {
        this.teamName = teamName;
        NoOfAuths = noOfAuths;
        this.phase1Answers = phase1Answers;
    }

    public Team(int noOfAuths) {
        NoOfAuths = noOfAuths;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getNoOfAuths() {
        return NoOfAuths;
    }

    public void setNoOfAuths(int noOfAuths) {
        NoOfAuths = noOfAuths;
    }

    public int getPhase1Answers() {
        return phase1Answers;
    }

    public void setPhase1Answers(int phase1Answers) {
        this.phase1Answers = phase1Answers;
    }

    @Exclude
    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("TeamName", teamName);
        result.put("NoOfAuths", NoOfAuths);
        result.put("phase1Answer", phase1Answers);
        return result;
    }}
