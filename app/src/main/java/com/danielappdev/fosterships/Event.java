package com.danielappdev.fosterships;

import java.util.Random;

public class Event {
    private int eventID;
    private String eventName;
    private String eventAdminEmail;
    private String eventExpectedNoOfPpl;

    public Event(){
        //instantiate
    }
    public Event(String eventName,String eventAdminEmail,String eventExpectedNoOfPpl){
        this.eventID=generateEventID();
        this.eventName=eventName;
        this.eventAdminEmail=eventAdminEmail;
        this.eventExpectedNoOfPpl=eventExpectedNoOfPpl;
    }

    public String getEventName() {
        return eventName;
    }
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;

    }

    public String getEventAdminEmail() {
        return eventAdminEmail;
    }

    public void setEventAdminEmail(String eventAdminEmail) {
        this.eventAdminEmail = eventAdminEmail;
    }

    public String getEventExpectedNoOfPpl() {
        return eventExpectedNoOfPpl;
    }

    public void setEventExpectedNoOfPpl(String eventExpectedNoOfPpl) {
        this.eventExpectedNoOfPpl = eventExpectedNoOfPpl;
    }

    //Actual methods
    private int generateEventID() {
        Random rand = new Random();
        int n = rand.nextInt(10000) + 1;
        eventID = n;
        return eventID;
    }
}
