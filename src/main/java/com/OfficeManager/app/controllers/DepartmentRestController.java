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


    /* ----- DEPARTMENT CONTROLLER ----- */

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

        // Test if the name is authorised
        if (!departmentService.isAuthorisedName(updateDepartmentDto.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Department department = new Department(updateDepartmentDto.getName());
        departmentService.saveDepartment(department);
        Team team = new Team(updateDepartmentDto.getName());
        team.setDepartment(department);
        teamService.saveTeam(team);
        department.getTeams().add(team);
        return new ResponseEntity<DepartmentDto>(mapDepartmentToDepartmentDto(department),HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Integer id, @RequestBody UpdateDepartmentDto updateDepartmentDto){

        // Test if the name is authorised
        if (!departmentService.isAuthorisedName(updateDepartmentDto.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (departmentService.findById(id).isPresent()){
            Department department = departmentService.findById(id).get();
            Optional<Team> optTeam = teamService.findByName(department.getName());
            if(optTeam.isPresent()){
                Team defaultTeam = optTeam.get();
                defaultTeam.setName(updateDepartmentDto.getName());
                teamService.saveTeam(defaultTeam);
            }
            department.setName(updateDepartmentDto.getName());
            departmentService.saveDepartment(department);
            return new ResponseEntity<>(mapDepartmentToDepartmentDto(department), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable int id){
        if (departmentService.findById(id).isPresent()){
            departmentService.swtichTeamToDefaultDepartment(id);
            departmentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /* ----- TEAM CONTROLLER ------ */

    @GetMapping("{id}/teams")
    ResponseEntity<List<TeamDto>> getTeam(@PathVariable int id) {
        Optional<Department> optDep = departmentService.findById(id);
        if(optDep.isPresent()) {
            Department department = optDep.get();
            return new ResponseEntity<>(mapDepartmentToDepartmentDto(department).getTeams(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/teams")
    ResponseEntity<TeamDto> addTeam(@PathVariable int id, @RequestBody UpdateTeamDto updateTeamDto) {

        // Test if the name is authorised
        if (!teamService.isAuthorisedName(updateTeamDto.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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

    @PutMapping("{idD}/teams/{idT}")
    ResponseEntity<TeamDto> updateTeam(@PathVariable int idD, @PathVariable int idT, @RequestBody UpdateTeamDto updateTeamDto){

        // Test if the name is authorised
        if (!teamService.isAuthorisedName(updateTeamDto.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Team> optTeam = teamService.findById(idT);
        if (optTeam.isPresent()){

            Team team = optTeam.get();
            team.setName(updateTeamDto.getName());
            teamService.saveTeam(team);

            return new ResponseEntity<>(new TeamDto(team), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{idD}/teams/{idT}")
    ResponseEntity<TeamDto> deleteTeam(@PathVariable int idD, @PathVariable int idT){

        Optional<Department> optDep = departmentService.findById(idD);
        if (optDep.isPresent()) {

            Department department = optDep.get();
            Team defaultTeam = teamService.findByName(department.getName()).get();
            Optional<Team> optTeam = teamService.findById(idT);

            if (optTeam.isPresent()) {
                teamService.switchPersonTODefaultTeam(idD, idT);
                teamService.deleteById(idT);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Map the department to the dto
     */
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
