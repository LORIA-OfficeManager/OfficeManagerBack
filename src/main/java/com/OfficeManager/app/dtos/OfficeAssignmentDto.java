package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.OfficeAssignment;

import java.time.LocalDate;

public class OfficeAssignmentDto {

    private LocalDate startDateAffectation, endDateAffectation;
    long startDateContract, endDateContract;
    private PersonDto person;

    public OfficeAssignmentDto(OfficeAssignment officeAssignment) {
        this.person = new PersonDto(officeAssignment.getPerson());
        this.startDateAffectation = officeAssignment.getStartDate();
        this.endDateAffectation = officeAssignment.getEndDate();
        //date déjà en timestamp
        this.startDateContract = person.getStartDateContract();
        this.endDateContract = person.getEndDateContract();
    }

    //timestamp format : nb milisec since 1970
    public long getStartDateAffectation() {
        return startDateAffectation.toEpochDay()*24*60*60*1000;
    }

    public void setStartDateAffectation(LocalDate startDateAffectation) {
        this.startDateAffectation = startDateAffectation;
    }

    //date déjà en timestamp
    public long getStartDateContract() {
        return startDateContract;
    }

    public void setStartDateContract(long startDateContract) {
        this.startDateContract = startDateContract;
    }

    public long getEndDateContract() {
        return endDateContract;
    }

    public void setEndDateContract(long endDateContract) {
        this.endDateContract = endDateContract;
    }

    //timestamp format : nb milisec since 1970
    public long getEndDateAffectation() {
        return endDateAffectation.toEpochDay()*24*60*60*1000;
    }

    public void setEndDateAffectation(LocalDate endDateAffectation) {
        this.endDateAffectation = endDateAffectation;
    }

    public Integer getId() {
        return person.getId();
    }

    public String getFirstName() {
        return person.getFirstName();
    }

    public String getLastName() {
        return person.getLastName();
    }

    public String getStatus() { return person.getStatus().getName(); }

    public void setPerson(PersonDto person) {
        this.person = person;
    }
}
