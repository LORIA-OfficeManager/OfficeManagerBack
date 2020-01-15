package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Office;

import java.util.List;
import java.util.Optional;

public interface IOfficeService {

    public List<Office> fetchAll();

    public Optional<Office> findById(Integer id);

    public Office saveOffice(Office office);

    public List<Office> saveAllOffice(List<Office> offices);

    List<String[]> fetchAllFullName();
}
