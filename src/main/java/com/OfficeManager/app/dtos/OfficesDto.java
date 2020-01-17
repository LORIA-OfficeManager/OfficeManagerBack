package com.OfficeManager.app.dtos;

public class OfficesDto {
    private Integer id, floor;
    private Double occupation;
    private Double size;
    private String building, num;
    private Boolean hasStranger;

    public Double getOccupation() {
        return occupation;
    }

    public void setOccupation(Double occupation) {
        this.occupation = occupation;
    }

    public Boolean getHasStranger() {
        return hasStranger;
    }

    public void setHasStranger(Boolean hasStranger) {
        this.hasStranger = hasStranger;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
