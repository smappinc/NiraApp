package com.nira.niradroid.activities;

public class ReportActivityObject {

    // string variables
    private String problem, station, yourname, date, yourapplicationid, yourID, yourphonenumber, staffName, comments;

    // an empty constructor is required when using Firebase Database
    public ReportActivityObject() {

    }

    public ReportActivityObject(String problem, String station, String yourname, String date, String yourapplicationid, String yourID, String yourphonenumber, String staffName, String comments){

        this.problem = problem;
        this.station = station;
        this.yourname = yourname;
        this.date = date;
        this.yourapplicationid = yourapplicationid;
        this.yourID = yourID;
        this.yourphonenumber =yourphonenumber;
        this.staffName = staffName;
        this.comments = comments;
    }

    // created getter and setter methods for all of our variables.
    // IMPORTANT! Remember to keep the same order of variables
    // as they appear in the activity_report XML layout
    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    //Date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //staffname
    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    //yourname
    public String getYourname() {
        return yourname;
    }

    public void setYourname(String yourname) {
        this.yourname = yourname;
    }

    //yourID
    public String getYourID() {
        return yourID;
    }

    public void setYourID(String yourID) {
        this.yourID = yourID;
    }

    //yourphonenumber
    public String getYourphonenumber() {
        return yourphonenumber;
    }

    public void setYourphonenumber(String yourphonenumber) {
        this.yourphonenumber = yourphonenumber;
    }

    //yourapplicationID
    public String getYourapplicationid() {
        return yourapplicationid;
    }

    public void setYourapplicationid(String yourapplicationid) {
        this.yourapplicationid = yourapplicationid;
    }

    //station
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    //comments
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
