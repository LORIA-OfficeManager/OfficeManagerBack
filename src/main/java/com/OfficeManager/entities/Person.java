package com.OfficeManager.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERSON")
public class Person {

    private Integer id;
    private String firstName,lastName;
    private Boolean isManager;

    Set<OfficeAssignment> assignments = new HashSet<OfficeAssignment>();

    public Person(String firstName, String lastName, Boolean isManager) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.isManager = isManager;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.person", cascade = CascadeType.ALL)
    public Set<OfficeAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<OfficeAssignment> assignments) {
        this.assignments = assignments;
    }

}
