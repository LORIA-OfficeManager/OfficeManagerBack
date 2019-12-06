package com.OfficeManager.services.interfaces;

import com.OfficeManager.entities.Office;

import java.util.List;

public interface IOfficeService {

    public List<Office> fetchAll();

    public Office findById(Integer id);

    public Office findByFullName(Integer num, Integer floor, String building);

    public Office saveOffice(Integer num, Integer floor, String building, Double size);
}
