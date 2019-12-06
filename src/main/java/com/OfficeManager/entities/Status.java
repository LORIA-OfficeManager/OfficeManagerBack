package com.OfficeManager.entities;

import javax.persistence.*;

@Entity
@Table(name = "STATUS")
public class Status {

    private Integer id;
    private Double size;
    private String name;

    public Status(Double size, String name) {
        super();
        this.size = size;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATUS_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "SIZE", nullable = false)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
