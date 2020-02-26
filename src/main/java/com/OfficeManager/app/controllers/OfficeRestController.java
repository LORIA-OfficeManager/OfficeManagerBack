package com.OfficeManager.app.controllers;


import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.entities.OfficeAssignment;
import com.OfficeManager.app.entities.Person;
import com.OfficeManager.app.services.impl.OfficeAssignmentServiceImpl;
import com.OfficeManager.app.services.impl.OfficeServiceImpl;
import com.OfficeManager.app.services.impl.StatusServiceImpl;
import com.OfficeManager.app.dtos.OfficeAssignmentDto;
import com.OfficeManager.app.dtos.OfficesDto;
import com.OfficeManager.app.dtos.SingleOfficeDto;
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

    @Autowired
    StatusServiceImpl statusService;

    @GetMapping("")
    public ResponseEntity<List<OfficesDto>> getOffices(){
        List<Office> offices = officeService.fetchAll();
        //Liste des occupations par bureau
        List<Double> occupations = new ArrayList<Double>();
        for(Office office: offices){
            occupations.add(this.findOccupationByOfficeId(office.getId()));
        }
        //List de si il y a un étranger par bureau
        List<Boolean> hasStrangers = new ArrayList<Boolean>();
        for(Office office: offices){
            hasStrangers.add(officeAssignmentService.hasStrangerByOfficeId(office.getId()));
        }
        List<OfficesDto> officesDTO = mapOfficesDtosFromOffices(offices, occupations, hasStrangers);
        return new ResponseEntity<List<OfficesDto>>(officesDTO, HttpStatus.OK);
    }

    @GetMapping("date/{timestamp}")
    public ResponseEntity<List<OfficesDto>> getOffices(@PathVariable long timestamp){
        List<Office> offices = officeService.fetchAll();
        //Liste des occupations par bureau
        List<Double> occupations = new ArrayList<Double>();
        List<Boolean> hasStrangers = new ArrayList<Boolean>();
        for(Office office: offices) {
            Double occupation = 0.0;
            boolean strangers =false;
            List<OfficeAssignment> officeAssignments = officeAssignmentService.findByOfficeID(office.getId(), true);
            for(OfficeAssignment oa : officeAssignments) {
                if (timestamp > oa.getStartDate().toEpochDay()*24*60*60*1000 &&
                   timestamp < oa.getEndDate().toEpochDay()*24*60*60*1000){
                   occupation += oa.getPerson().getStatus().getSize();
                 }
                Person p = oa.getPerson();
                if ( (timestamp < p.getStartDateContract().toEpochDay()*24*60*60*1000) ||
                        (timestamp > p.getEndDateContract().toEpochDay()*24*60*60*1000) ){
                    strangers = true;
                }
            }
            occupations.add(occupation);
            hasStrangers.add(strangers);
        }
        List<OfficesDto> officesDTO = mapOfficesDtosFromOffices(offices, occupations, hasStrangers);
        return new ResponseEntity<List<OfficesDto>>(officesDTO, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SingleOfficeDto> getOffice(@PathVariable Integer id){
        if (officeService.findById(id).isPresent()){
            List<OfficeAssignment> officeAssignments = officeAssignmentService.findByOfficeID(id, true);
            Office office = officeService.findById(id).get();
            Boolean hasStranger = officeAssignmentService.hasStrangerByOfficeId(id);
            SingleOfficeDto officeDTO = mapOfficeDtoFromOffice(office, officeAssignments, this.findOccupationByOfficeId(office.getId()), hasStranger);
            return new ResponseEntity<SingleOfficeDto>(officeDTO, HttpStatus.OK);
        }
        return new ResponseEntity<SingleOfficeDto>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/capacity")
    public ResponseEntity<String> updateCapa(@RequestBody OfficesDto office) {
        Office o = officeService.findById(office.getId()).get();
        o.setSize(office.getSize());
        officeService.saveOffice(o);
        return new ResponseEntity<String>("{\"message\":\" Nouvelle taille du bureau : "+office.getSize()+".\"}", HttpStatus.OK);
    }

    private List<OfficesDto> mapOfficesDtosFromOffices(List<Office> offices, List<Double> occupation, List<Boolean> hasStrangers) {
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

    private SingleOfficeDto mapOfficeDtoFromOffice(Office office, List<OfficeAssignment> officeAssignments, Double occupation, Boolean hasStranger){
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

    private Double findOccupationByOfficeId(int id){
        //Ici on fait la somme des tailles des status des personnes présentes dans un bureaux
        List<Integer> statusId = officeAssignmentService.findAllStatusByOfficeId(id);
        Double sum = 0.0;

        for (Integer i: statusId) {
            sum += statusService.findById(i).get().getSize();
        }

        return sum;
    }

}
