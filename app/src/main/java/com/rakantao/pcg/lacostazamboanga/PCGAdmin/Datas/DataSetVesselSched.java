package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Datas;

public class DataSetVesselSched {

    public String day;
    public String locations;
    public String times;
    public String decision;

    public DataSetVesselSched() {

    }

    public DataSetVesselSched(String day, String locations, String times, String decision) {
        this.day = day;
        this.locations = locations;
        this.times = times;
        this.decision = decision;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLocation() {
        return locations;
    }

    public void setLocation(String location) {
        this.locations = location;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
