package com.OfficeManager.app.controllers;


import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.services.impl.OfficeServiceImpl;
import com.OfficeManager.dtos.OfficeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
