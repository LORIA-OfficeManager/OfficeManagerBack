package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IOfficeDao;
import com.OfficeManager.app.entities.Office;
import com.OfficeManager.app.services.interfaces.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("officeService")
@Transactional
public class OfficeServiceImpl implements IOfficeService {

    @Autowired
    private IOfficeDao officeDao;

    @Override
    public List<Office> fetchAll() {
        return officeDao.findAll();
    }

    @Override
    public Optional<Office> findById(Integer id) {
        return officeDao.findById(id);
    }

    @Override
    public Office saveOffice(Office office) {
        return officeDao.save(office);
    }

    @Override
    public List<Office> saveAllOffice(List<Office> offices){
        return officeDao.saveAll(offices);
    }
}
