package com.devglan.reactiveapidemo.model;

public class UserEvent {

    private String eventId;
    private String eventType;

    public UserEvent(String eventId, String eventType){
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
