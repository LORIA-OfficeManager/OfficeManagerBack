package com.OfficeManager.app.dtos;

import com.OfficeManager.app.entities.Team;

public class TeamDto {
    private Integer id;
    private String name;

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
