package com.OfficeManager.app.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OFFICE")
public class Office {

    private Integer id, floor, num;
    private Double size;
    private String building, description;

    Set<OfficeAssignment> assignments = new HashSet<OfficeAssignment>();

    public Office(){

    }

    public Office(Double size, Integer floor, Integer num, String building, String description) {
        super();
        this.size = size;
        this.floor = floor;
        this.num = num;
        this.building = building;
        this.description = description==""?"Aucune description":description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OFFICE_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "FLOOR", nullable = false)
    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Column(name = "NUM", nullable = false)
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Column(name = "SIZE", nullable = false)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Column(name = "BUILDING", nullable = false)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office", cascade = CascadeType.ALL)
    public Set<OfficeAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<OfficeAssignment> assignments) {
        this.assignments = assignments;
    }
}