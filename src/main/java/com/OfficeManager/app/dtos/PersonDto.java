package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.*;

import java.time.LocalDate;
import java.util.Iterator;

public class PersonDto {
    private Integer id, officeId;
    private String firstname, lastname, officeName;
    private Boolean isManager;
    private LocalDate startDateContract, endDateContract;
    private Status status;
    private Team team;
    private Department department;
    private Integer assignment_id;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.firstname = person.getFirstName();
        this.lastname = person.getLastName();
        this.isManager = person.getManager();
        this.startDateContract = person.getStartDateContract();
        this.endDateContract = person.getEndDateContract();
        this.status = person.getStatus();
        this.team = person.getTeam();
        this.department = person.getDepartment();
        for(Iterator<OfficeAssignment> it = person.getAssignments().iterator(); it.hasNext();){
            //Si il existe un assignement actuellement sur cet personne
            OfficeAssignment oa = it.next();
            if (System.currentTimeMillis() > oa.getStartDate().toEpochDay()*24*60*60*1000 && System.currentTimeMillis() < oa.getEndDate().toEpochDay()*24*60*60*1000){
                this.assignment_id = oa.getId();
                this.officeId = oa.getOffice().getId();
                this.officeName = oa.getOffice().getBuilding()+oa.getOffice().getFloor()+oa.getOffice().getNum();
                break;
            } else {
                this.assignment_id = null;
                this.officeId = null;
                this.officeName = null;
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    //Protected fait en sorte que cela ne s'affiche pas dans le retour de la requête
    protected Boolean getManager() {
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

    public Integer getIdAssignement() {
        return assignment_id;
    }

    public void setAssignment_id(Integer assignment_id) {
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

    public String getOfficeName() {
        return officeName;
    }

    public void setOffice(String office) {
        this.officeName = office;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }
}
