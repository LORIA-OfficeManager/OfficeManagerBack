package com.OfficeManager.app.controllers;

import com.OfficeManager.app.dtos.TeamDto;
import com.OfficeManager.app.entities.Team;
import com.OfficeManager.app.services.impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/team", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
public class TeamRestController {

    @Autowired
    TeamServiceImpl teamService;

    @GetMapping("")
    ResponseEntity<List<TeamDto>> getTeams(){
        ArrayList<TeamDto> list = new ArrayList<TeamDto>();
        for (Team t: teamService.fetchAll()) {
            list.add(mapTeamtoTeamDto(t));
        }
        return new ResponseEntity<List<TeamDto>>(list, HttpStatus.OK);
    }

    public TeamDto mapTeamtoTeamDto(Team team){
        return new TeamDto(team);
    }
}
