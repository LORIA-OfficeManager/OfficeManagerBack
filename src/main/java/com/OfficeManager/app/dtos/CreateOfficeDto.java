package com.OfficeManager.app.dtos;

public class CreateOfficeDto {

    private Integer floor;
    private Double size;
    private String building, description, num;

    public CreateOfficeDto(Integer floor, Double size, String building, String description, String num) {
        this.floor = floor;
        this.size = size;
        this.building = building;
        this.description = description;
        this.num = num;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
