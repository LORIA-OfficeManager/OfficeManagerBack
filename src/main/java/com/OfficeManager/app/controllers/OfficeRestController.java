package com.OfficeManager.app.controllers;


import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.services.impl.OfficeServiceImpl;
import com.OfficeManager.dtos.OfficeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/office", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfficeRestController {
    @Autowired
    OfficeServiceImpl officeService;

    @GetMapping("")
    public ResponseEntity<List<OfficeDTO>> getOffices(){
        List<Office> offices = officeService.fetchAll();
        List<OfficeDTO> officesDTO = mapOfficesDtosFromOffices(offices);
        return new ResponseEntity<List<OfficeDTO>>(officesDTO, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<OfficeDTO> getOffice(@PathVariable Integer id){
        if (officeService.findById(id).isPresent()){
            Office office = officeService.findById(id).get();
            OfficeDTO officeDTO = mapOfficeDtoFromOffice(office);
            return new ResponseEntity<OfficeDTO>(officeDTO, HttpStatus.OK);
        }

        return new ResponseEntity<OfficeDTO>(HttpStatus.NO_CONTENT);

    }


    private List<OfficeDTO> mapOfficesDtosFromOffices(List<Office> offices) {

        Function<Office, OfficeDTO> mapperFunction = (office) -> {
            OfficeDTO officeDTO = new OfficeDTO();
            officeDTO.setId(office.getId());
            officeDTO.setBuilding(office.getBuilding());
            officeDTO.setDescription(office.getDescription());
            officeDTO.setFloor(office.getFloor());
            officeDTO.setNum(office.getNum());
            officeDTO.setSize(office.getSize());


            return officeDTO;
        };

        if (!CollectionUtils.isEmpty(offices)) {
            return offices.stream().map(mapperFunction).collect(Collectors.toList());
        }
        return null;
    }

    private OfficeDTO mapOfficeDtoFromOffice(Office office){
        OfficeDTO officeDTO = new OfficeDTO();
        officeDTO.setId(office.getId());
        officeDTO.setBuilding(office.getBuilding());
        officeDTO.setDescription(office.getDescription());
        officeDTO.setFloor(office.getFloor());
        officeDTO.setNum(office.getNum());
        officeDTO.setSize(office.getSize());

        return officeDTO;
    }

}
