package com.danielappdev.fosterships;

import android.app.Application;
import android.util.Log;

import java.util.Random;


public class Event extends Application {
    private static int eventID;
    private String eventName;


    public Event(){
        //instantiate
    }
    public Event(int ID){
        this.eventID = ID;

    }

    public String getEventName() {
        return eventName;
    }
    public int getEventID() {
        return eventID;
    }





}




