package com.OfficeManager.app.controllers;


import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.entities.OfficeAssignment;
import com.OfficeManager.app.services.impl.OfficeAssignmentServiceImpl;
import com.OfficeManager.app.services.impl.OfficeServiceImpl;
import com.OfficeManager.dtos.OfficeAssignmentDto;
import com.OfficeManager.dtos.OfficesDto;
import com.OfficeManager.dtos.SingleOfficeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/office", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfficeRestController {
    @Autowired
    OfficeServiceImpl officeService;

    @Autowired
    OfficeAssignmentServiceImpl officeAssignmentService;

    @GetMapping("")
    public ResponseEntity<List<OfficesDto>> getOffices(){
        List<Office> offices = officeService.fetchAll();
        //Liste des occupations par bureau
        List<Integer> occupations = new ArrayList<Integer>();
        for(Office office: offices){
            occupations.add(officeAssignmentService.findOccupationByOfficeId(office.getId()));
        }
        //List de si il y a un étranger par bureau
        List<Boolean> hasStrangers = new ArrayList<Boolean>();
        for(Office office: offices){
            hasStrangers.add(officeAssignmentService.hasStrangerByOfficeId(office.getId()));
        }
        List<OfficesDto> officesDTO = mapOfficesDtosFromOffices(offices, occupations, hasStrangers);
        return new ResponseEntity<List<OfficesDto>>(officesDTO, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SingleOfficeDto> getOffice(@PathVariable Integer id){
        if (officeService.findById(id).isPresent()){
            List<OfficeAssignment> officeAssignments = officeAssignmentService.findByOfficeID(id);
            Office office = officeService.findById(id).get();
            Boolean hasStranger = officeAssignmentService.hasStrangerByOfficeId(id);
            SingleOfficeDto officeDTO = mapOfficeDtoFromOffice(office, officeAssignments, officeAssignmentService.findOccupationByOfficeId(id), hasStranger);
            return new ResponseEntity<SingleOfficeDto>(officeDTO, HttpStatus.OK);
        }
        return new ResponseEntity<SingleOfficeDto>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOffice(@RequestBody Office office) {
        officeService.saveOffice(office);
        return new ResponseEntity<String>("Bureau avec l'id:"+office.getId()+" bien créé",HttpStatus.CREATED);
    }


    private List<OfficesDto> mapOfficesDtosFromOffices(List<Office> offices, List<Integer> occupation, List<Boolean> hasStrangers) {
        List<OfficesDto> officesDtos = new ArrayList<OfficesDto>();

        for (int i = 0 ; i < offices.size() ; i++){
            OfficesDto officeDTO = new OfficesDto();
            officeDTO.setId(offices.get(i).getId());
            officeDTO.setBuilding(offices.get(i).getBuilding());
            officeDTO.setFloor(offices.get(i).getFloor());
            officeDTO.setNum(offices.get(i).getNum());
            officeDTO.setSize(offices.get(i).getSize());
            officeDTO.setOccupation(occupation.get(i));
            officeDTO.setHasStranger(hasStrangers.get(i));

            officesDtos.add(officeDTO);
        }

        return officesDtos;
    }

    private SingleOfficeDto mapOfficeDtoFromOffice(Office office, List<OfficeAssignment> officeAssignments, Integer occupation, Boolean hasStranger){
        //Pour voir la structure des objets renvoyé cf la photo d'après réunion
        OfficesDto officeDTO = new OfficesDto();
        officeDTO.setId(office.getId());
        officeDTO.setBuilding(office.getBuilding());
        officeDTO.setFloor(office.getFloor());
        officeDTO.setNum(office.getNum());
        officeDTO.setSize(office.getSize());
        officeDTO.setOccupation(occupation);
        officeDTO.setHasStranger(hasStranger);

        SingleOfficeDto singleOfficeDto = new SingleOfficeDto();
        singleOfficeDto.setOffice(officeDTO);
        singleOfficeDto.setDescription(office.getDescription());

        List<OfficeAssignmentDto> officeAssignmentDto = new ArrayList<OfficeAssignmentDto>();

        for (OfficeAssignment o: officeAssignments) {
            OfficeAssignmentDto oadto = new OfficeAssignmentDto(o);
            officeAssignmentDto.add(oadto);
        }

        singleOfficeDto.setPersons(officeAssignmentDto);

        return singleOfficeDto;
    }

}
