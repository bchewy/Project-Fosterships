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

    public String getEventName() {
        return eventName;
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
}
