package com.OfficeManager.app.entities;

import javax.persistence.*;

@Entity
@Table(name = "TEAM")
public class Team {

    private Integer id;
    private String name;

    public Team(){

    }

    public Team(String name) {
        super();
        this.name = name;
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
}