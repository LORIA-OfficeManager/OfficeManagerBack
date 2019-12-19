package com.OfficeManager.app.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERSON")
public class Person {

    private Integer id;
    private String firstName,lastName, email;
    private Boolean isManager;
    private LocalDate startDateContract, endDateContract;
    private Status status;
    private Team team;
    private Department department;

    Set<OfficeAssignment> assignments = new HashSet<OfficeAssignment>();

    public Person(){

    }

    public Person(String firstName, String lastName, String email, Boolean isManager, LocalDate startDateContract, LocalDate endDateContract, Status status, Team team, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isManager = isManager;
        this.startDateContract = startDateContract;
        this.endDateContract = endDateContract;
        this.status = status;
        this.team = team;
        this.department = department;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PERSON_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "IS_MANAGER", nullable = false)
    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    @Column(name = "START_DATE_CONTRACT", nullable = false)
    public LocalDate getStartDateContract() {
        return startDateContract;
    }

    public void setStartDateContract(LocalDate startDateContract) {
        this.startDateContract = startDateContract;
    }

    @Column(name = "END_DATE_CONTRACT", columnDefinition = "date default '2099-12-31'")
    public LocalDate getEndDateContract() {
        return endDateContract;
    }

    public void setEndDateContract(LocalDate endDateContract) {
        this.endDateContract = endDateContract;
    }

    @Column(name = "EMAIL", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
    public Set<OfficeAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<OfficeAssignment> assignments) {
        this.assignments = assignments;
    }

    @ManyToOne(optional = false)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne()
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @ManyToOne(optional = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
