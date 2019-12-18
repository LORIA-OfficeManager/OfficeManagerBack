package com.OfficeManager.app.dtos;

import java.util.List;

public class SingleOfficeDto {
    private OfficesDto office;
    private String description;
    private List<OfficeAssignmentDto> persons;

    public List<OfficeAssignmentDto> getPersons() {
        return persons;
    }

    public void setPersons(List<OfficeAssignmentDto> persons) {
        this.persons = persons;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OfficesDto getOffice() {
        return office;
    }

    public void setOffice(OfficesDto office) {
        this.office = office;
    }

    /*  SOIT GET OFFICEDTO SOIT LE CODE COMMENTÉ

        public Integer getOccupation() {
            return office.getOccupation();
        }

        public void setOccupation(Integer occupation) {
            this.office.setOccupation(occupation);
        }

        public Boolean getHasStranger() {
            return office.getHasStranger();
        }

        public void setHasStranger(Boolean hasStranger) {
            this.office.setHasStranger(hasStranger);
        }

        public Integer getId() {
            return office.getId();
        }

        public void setId(Integer id) {
            this.office.setId(id);
        }

        public Integer getFloor() {
            return office.getFloor();
        }

        public void setFloor(Integer floor) {
            this.office.setFloor(floor);
        }

        public String getNum() {
            return office.getNum();
        }

        public void setNum(String num) {
            this.office.setNum(num);
        }

        public Double getSize() {
            return office.getSize();
        }

        public void setSize(Double size) {
            this.office.setSize(size);
        }

        public String getBuilding() {
            return office.getBuilding();
        }

        public void setBuilding(String building) {
            this.office.setBuilding(building);
        }
    */

}
