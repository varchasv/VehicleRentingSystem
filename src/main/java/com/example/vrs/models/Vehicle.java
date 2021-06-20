package com.example.vrs.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehicle implements Cloneable{

    @JsonProperty
    private String regNo;

    @JsonProperty
    private String barCode;

    @JsonProperty
    private VehicleType vehicleType;

    private boolean isAvailable;

    private int parkingNo;

    public int getParkingNo() {
        return parkingNo;
    }

    public void setParkingNo(int parkingNo) {
        this.parkingNo = parkingNo;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Vehicle() {
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
