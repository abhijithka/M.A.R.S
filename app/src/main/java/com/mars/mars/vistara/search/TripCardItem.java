package com.mars.mars.vistara.search;

/**
 * Created by aappukuttan on 10/6/2017.
 */
public class TripCardItem {

    private String depAirportCode;
    private String arrAirportCode;
    private long timeInMillis;
    private String flightNumber;

    public TripCardItem(String depAirportCode, String arrAirportCode, String date, String flightNumber) {
        this.depAirportCode = depAirportCode;
        this.arrAirportCode = arrAirportCode;
        this.timeInMillis = timeInMillis;
        this.flightNumber = flightNumber;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getArrAirportCode() {
        return arrAirportCode;
    }

    public void setArrAirportCode(String arrAirportCode) {
        this.arrAirportCode = arrAirportCode;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setDate(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
