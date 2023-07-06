package com.tinieblas.tokomegawa.domain.models;

public class LocationData {
    private String district;
    private String department;


    public LocationData(String district, String department) {
        this.district = district;
        this.department = department;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
