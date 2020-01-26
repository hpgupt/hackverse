package com.example.findmywayoffline;

public class UserDetails {

    String lat,longi,timest,body,number;

    public UserDetails(String lat, String longi, String timest, String body, String number) {
        this.lat = lat;
        this.longi = longi;
        this.timest = timest;
        this.body = body;
        this.number = number;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getTimest() {
        return timest;
    }

    public void setTimest(String timest) {
        this.timest = timest;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
