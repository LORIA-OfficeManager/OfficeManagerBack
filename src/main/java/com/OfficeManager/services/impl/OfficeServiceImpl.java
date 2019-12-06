package com.OfficeManager.services.impl;

import com.OfficeManager.daos.IOfficeDao;
import com.OfficeManager.entities.Office;
import com.OfficeManager.services.interfaces.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("officeService")
@Transactional
public class OfficeServiceImpl implements IOfficeService {

    @Autowired
    private IOfficeDao officeDao;

    @Override
    public List<Office> fetchAll() {
        return officeDao.fetchAll();
    }

    @Override
    public Office findById(Integer id) {
        return officeDao.findByOfficeId(id);
    }

    @Override
    public Office findByFullName(Integer num, Integer floor, String building) {
        return findByFullName(num,floor,building);
    }

    @Override
    public Office saveOffice(Integer num, Integer floor, String building, Double size) {
        return officeDao.save(new Office(size,floor,num,building,""));
    }
}
