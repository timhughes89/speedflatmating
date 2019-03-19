package com.spareroom.speedflatmating.model;

public class EventHeader implements EventSection {

    private final int month;

    public EventHeader(final int month) {
        this.month = month;
    }

    public String getHeader() {
        String monthString;
        switch (month) {
            case 0:
                monthString = "January";
                break;
            case 1:
                monthString = "February";
                break;
            default:
                monthString = "Unknown";
        }

        return monthString;
    }
}
