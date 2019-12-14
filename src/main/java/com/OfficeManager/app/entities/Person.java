package com.OfficeManager.app.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERSON")
public class Person {

    private Integer id;
    private String firstName,lastName;
    private Boolean isManager;
    private Double size;
    private LocalDate startDateContract, endDateContract;

    Set<OfficeAssignment> assignments = new HashSet<OfficeAssignment>();

    public Person(){

    }

    public Person(String firstName, String lastName, Boolean isManager, Double size) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.isManager = isManager;
        this.size = size;
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

    @Column(name = "SIZE", nullable = false)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
    public Set<OfficeAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<OfficeAssignment> assignments) {
        this.assignments = assignments;
    }

}
