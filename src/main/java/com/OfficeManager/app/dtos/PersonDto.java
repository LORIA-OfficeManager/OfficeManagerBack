package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.*;

import java.time.LocalDate;

public class PersonDto {
    private Integer id;
    private String firstName,lastName, office;
    private Boolean isManager;
    private LocalDate startDateContract, endDateContract;
    private Status status;
    private Team team;
    private Department department;
    private int assignment_id;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.isManager = person.getManager();
        this.startDateContract = person.getStartDateContract();
        this.endDateContract = person.getStartDateContract();
        this.status = person.getStatus();
        this.team = person.getTeam();
        this.department = person.getDepartment();
        for(OfficeAssignment oa :person.getAssignments()){
            //Si il existe un assignement actuellement sur cet personne
            if (System.currentTimeMillis() > oa.getStartDate().toEpochDay()*24*60*60*1000 && System.currentTimeMillis() < oa.getEndDate().toEpochDay()*24*60*60*1000){
                this.assignment_id = oa.getId();
                this.office = oa.getOffice().getBuilding()+oa.getOffice().getFloor()+oa.getOffice().getNum();
            }
        }
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

    //Protected fait en sorte que cela ne s'affiche pas dans le retour de la requête
    protected Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    //timestamp format : nb milisec since 1970
    protected long getStartDateContract() {
        return startDateContract.toEpochDay()*24*60*60*1000;
    }

    //timestamp format : nb milisec since 1970
    public void setStartDateContract(LocalDate startDateContract) {
        this.startDateContract = startDateContract;
    }

    protected long getEndDateContract() {
        return endDateContract.toEpochDay()*24*60*60*1000;
    }

    public void setEndDateContract(LocalDate endDateContract) {
        this.endDateContract = endDateContract;
    }

    public String getStatusName() {
        return status.getName();
    }

    //Protected fait en sorte que cela ne s'affiche pas dans le retour de la requête
    protected Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getTeamName() {
        return team.getName();
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getDepartmentName() {
        return department.getName();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
