package com.spareroom.speedflatmating.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private String imageUrl;
    private String location;
    private String venue;
    private String startTime;
    private String endTime;
    private String cost;
    private String phoneNumber = "0161 768 1162";

    public Event(String imageUrl, String location, String venue, String startTime, String endTime, String cost, String phoneNumber) {
        this.imageUrl = imageUrl;
        this.location = location;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(location);
        dest.writeString(venue);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(cost);
        dest.writeString(phoneNumber);
    }

    protected Event(Parcel in) {
        imageUrl = in.readString();
        location = in.readString();
        venue = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        cost = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
