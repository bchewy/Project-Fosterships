package com.danielappdev.fosterships;

public class Event {
    public String eventName;
    public String eventAdminEmail;
    public String eventExpectedNoOfPpl;

    public Event(){
        //instantiate
    }
    public Event(String eventName,String eventAdminEmail,String eventExpectedNoOfPpl){
        this.eventName=eventName;
        this.eventAdminEmail=eventAdminEmail;
        this.eventExpectedNoOfPpl=eventExpectedNoOfPpl;
    }

    private void CreateNewEvent(String eventName,String eventAdminEmail,String eventExpectedNoOfPpl){
        this.eventName=eventName;
        this.eventAdminEmail=eventAdminEmail;
        this.eventExpectedNoOfPpl=eventExpectedNoOfPpl;
    }
    public String getEventDetails(){
        return this.eventName;
    }
}
