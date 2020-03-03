package com.OfficeManager.app.controllers;

import com.OfficeManager.app.dtos.DepartmentDto;
import com.OfficeManager.app.dtos.TeamDto;
import com.OfficeManager.app.dtos.UpdateDepartmentDto;
import com.OfficeManager.app.dtos.UpdateTeamDto;
import com.OfficeManager.app.entities.Department;
import com.OfficeManager.app.entities.Team;
import com.OfficeManager.app.services.impl.DepartmentServiceImpl;
import com.OfficeManager.app.services.impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/department", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
public class DepartmentRestController {

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    TeamServiceImpl teamService;

    @GetMapping("")
    ResponseEntity<List<DepartmentDto>> getDepartments(){
        ArrayList<DepartmentDto> list = new ArrayList<DepartmentDto>();
        for (Department d: departmentService.fetchAll()) {
            list.add(mapDepartmentToDepartmentDto(d));
        }
        return new ResponseEntity<List<DepartmentDto>>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Integer id){
        if (departmentService.findById(id).isPresent()){
            Department department = departmentService.findById(id).get();
            DepartmentDto departmentDto = mapDepartmentToDepartmentDto(department);
            return new ResponseEntity<>(departmentDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    ResponseEntity<DepartmentDto> addDepartment(@RequestBody UpdateDepartmentDto updateDepartmentDto){
        Department department = new Department(updateDepartmentDto.getName());
        departmentService.saveDepartment(department);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Integer id, @RequestBody UpdateDepartmentDto updateDepartmentDto){
        if (departmentService.findById(id).isPresent()){
            Department department = departmentService.findById(id).get();
            department.setName(updateDepartmentDto.getName());
            departmentService.saveDepartment(department);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable int id){
        if (departmentService.findById(id).isPresent()){
            departmentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/teams")
    ResponseEntity<List<TeamDto>> getTeam(@PathVariable int id) {
        Optional<Department> optDep = departmentService.findById(id);
        if(optDep.isPresent()) {
            Department department = optDep.get();
            List<TeamDto> teamsDto = new ArrayList<>();
            department.getTeams().forEach(team -> teamsDto.add(new TeamDto(team)));
            return new ResponseEntity<>(teamsDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/teams")
    ResponseEntity<TeamDto> addTeam(@PathVariable int id, @RequestBody UpdateTeamDto updateTeamDto) {
        Optional<Department> optDep = departmentService.findById(id);
        if(optDep.isPresent()) {
            Department department = optDep.get();
            Team team = new Team(updateTeamDto.getName());
            team.setDepartment(department);
            teamService.saveTeam(team);
            return new ResponseEntity<TeamDto>(new TeamDto(team), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{idD}/teams/{idT}")
    ResponseEntity<TeamDto> deleteTeam(@PathVariable int idD, @PathVariable int idT){
        if (teamService.findById(idT).isPresent()){
            teamService.deleteById(idT);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{idD}/teams/{idT}")
    ResponseEntity<TeamDto> updateTeam(@PathVariable int idD, @PathVariable int idT, @RequestBody UpdateTeamDto updateTeamDto){
        Optional<Team> optTeam = teamService.findById(idT);
        if (optTeam.isPresent()){
            Team team = optTeam.get();
            team.setName(updateTeamDto.getName());
            teamService.saveTeam(team);
            return new ResponseEntity<>(new TeamDto(team), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public DepartmentDto mapDepartmentToDepartmentDto(Department department){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        List<TeamDto> teamsDto = new ArrayList<TeamDto>();
        department.getTeams().forEach(team -> {
            teamsDto.add(new TeamDto(team));
        });
        departmentDto.setTeams(teamsDto);
        return departmentDto;
    }

}
