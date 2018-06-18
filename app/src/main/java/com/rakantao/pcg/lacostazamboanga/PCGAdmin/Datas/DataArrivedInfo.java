package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Datas;

public class DataArrivedInfo {

    private String VesselType;
    private String VesselName;
    private String Origin;
    private String DepartureTime;
    private String Destination;
    private String ArrivalTime;
    private String ScheduleDay;
    private String HoursTravelled;
    private String ActualTimeArrived;

    public DataArrivedInfo(){

    }

    public DataArrivedInfo(String VesselType, String VesselName, String ScheduleDay, String Origin, String DepartureTime,
                           String Destination,String ArrivalTime,String HoursTravelled, String ActualTimeArrived){
        this.VesselType = VesselType;
        this.VesselName = VesselName;
        this.ScheduleDay = ScheduleDay;
        this.Origin = Origin;
        this.DepartureTime = DepartureTime;
        this.Destination = Destination;
        this.ArrivalTime = ArrivalTime;
        this.HoursTravelled = HoursTravelled;
        this.ActualTimeArrived = ActualTimeArrived;

    }

    public String getVesselType() {
        return VesselType;
    }

    public void setVesselType(String vesselType) {
        VesselType = vesselType;
    }

    public String getVesselName() {
        return VesselName;
    }

    public void setVesselName(String vesselName) {
        VesselName = vesselName;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getScheduleDay() {
        return ScheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        ScheduleDay = scheduleDay;
    }

    public String getHoursTravelled() {
        return HoursTravelled;
    }

    public void setHoursTravelled(String hoursTravelled) {
        HoursTravelled = hoursTravelled;
    }

    public String getActualTimeArrived() {
        return ActualTimeArrived;
    }

    public void setActualTimeArrived(String actualTimeArrived) {
        ActualTimeArrived = actualTimeArrived;
    }
}
