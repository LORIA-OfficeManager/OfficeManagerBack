package com.OfficeManager.dtos;

import com.OfficeManager.app.entities.Person;

import java.time.LocalDate;

public class PersonDto {
    private Integer id;
    private String firstName,lastName;
    private Boolean isManager;
    private LocalDate startDateContract, endDateContract;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.isManager = person.getManager();
        this.startDateContract = person.getStartDateContract();
        this.endDateContract = person.getStartDateContract();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    //timestamp format : nb milisec since 1970
    public long getStartDateContract() {
        return startDateContract.toEpochDay()*24*60*60*1000;
    }

    //timestamp format : nb milisec since 1970
    public void setStartDateContract(LocalDate startDateContract) {
        this.startDateContract = startDateContract;
    }

    public long getEndDateContract() {
        return endDateContract.toEpochDay()*24*60*60*1000;
    }

    public void setEndDateContract(LocalDate endDateContract) {
        this.endDateContract = endDateContract;
    }
}
