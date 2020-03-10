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

}
