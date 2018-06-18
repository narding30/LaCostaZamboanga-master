package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Datas;

public class DataSendReport {

    public String vesselName;
    public String timeUploaded;
    public String inspector;

    public DataSendReport() {
    }

    public DataSendReport(String vesselName, String timeUploaded, String inspector) {
        this.vesselName = vesselName;
        this.timeUploaded = timeUploaded;
        this.inspector = inspector;
    }



    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getTimeUploaded() {
        return timeUploaded;
    }

    public void setTimeUploaded(String timeUploaded) {
        this.timeUploaded = timeUploaded;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }
}
