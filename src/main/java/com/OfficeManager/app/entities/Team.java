package com.OfficeManager.app.entities;

import javax.persistence.*;

@Entity
@Table(name = "TEAM")
public class Team {

    private Integer id;
    private String name;
    private Department department;

    public Team(){

    }

    public Team(Integer id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEAM_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}