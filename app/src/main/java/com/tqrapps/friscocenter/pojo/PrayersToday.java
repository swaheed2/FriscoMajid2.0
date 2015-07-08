package com.tqrapps.friscocenter.pojo;

/**
 * Created by Sumama on 11/26/2014.
 */
public class PrayersToday  {
    private String date, fajrTime, fajrIqamah, sunrise, dhuhrTime, dhuhrIqamah, asrTime, asrIqamah, maghribTime,
            maghribIqamah, ishaTime, ishaIqamah;

    public PrayersToday (String date, String fajrTime,String fajrIqamah,String sunrise,String dhuhrTime,
                        String dhuhrIqamah, String asrTime, String  asrIqamah, String  maghribTime,
                        String maghribIqamah, String  ishaTime, String  ishaIqamah){
        this.date = date;
        this.fajrTime   = fajrTime  ;
        this.fajrIqamah  = fajrIqamah      ;
        this.sunrise   = sunrise ;
        this.dhuhrTime   = dhuhrTime  ;
        this.dhuhrIqamah  = dhuhrIqamah ;
        this.asrTime   = asrTime ;
        this.asrIqamah   = asrIqamah  ;
        this.maghribTime  = maghribTime   ;
        this.maghribIqamah = maghribIqamah   ;
        this.ishaTime = ishaTime   ;
        this.ishaIqamah = ishaIqamah   ;

    }
    public PrayersToday(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFajrTime() {
        return fajrTime;
    }

    public void setFajrTime(String fajrTime) {
        this.fajrTime = fajrTime;
    }

    public String getFajrIqamah() {
        return fajrIqamah;
    }

    public void setFajrIqamah(String fajrIqamah) {
        this.fajrIqamah = fajrIqamah;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getDhuhrTime() {
        return dhuhrTime;
    }

    public void setDhuhrTime(String dhuhrTime) {
        this.dhuhrTime = dhuhrTime;
    }

    public String getDhuhrIqamah() {
        return dhuhrIqamah;
    }

    public void setDhuhrIqamah(String dhuhrIqamah) {
        this.dhuhrIqamah = dhuhrIqamah;
    }

    public String getAsrTime() {
        return asrTime;
    }

    public void setAsrTime(String asrTime) {
        this.asrTime = asrTime;
    }

    public String getAsrIqamah() {
        return asrIqamah;
    }

    public void setAsrIqamah(String asrIqamah) {
        this.asrIqamah = asrIqamah;
    }

    public String getMaghribTime() {
        return maghribTime;
    }

    public void setMaghribTime(String maghribTime) {
        this.maghribTime = maghribTime;
    }

    public String getMaghribIqamah() {
        return maghribIqamah;
    }

    public void setMaghribIqamah(String maghribIqamah) {
        this.maghribIqamah = maghribIqamah;
    }

    public String getIshaTime() {
        return ishaTime;
    }

    public void setIshaTime(String ishaTime) {
        this.ishaTime = ishaTime;
    }

    public String getIshaIqamah() {
        return ishaIqamah;
    }

    public void setIshaIqamah(String ishaIqamah) {
        this.ishaIqamah = ishaIqamah;
    }
}
