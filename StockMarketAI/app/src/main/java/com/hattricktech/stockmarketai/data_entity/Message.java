package com.hattricktech.stockmarketai.data_entity;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Jainil on 05-02-2017.
 */

public class Message {
    private String message;
    Boolean isSend;
    Calendar calendar;

    public Message( Boolean isSend, Calendar calendar) {

        this.isSend = isSend;
        this.calendar = calendar;
    }

    public Message(String message, Boolean isSend, Calendar calendar) {
        this.message = message;
        this.isSend = isSend;
        this.calendar = calendar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSend() {
        return isSend;
    }



    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
