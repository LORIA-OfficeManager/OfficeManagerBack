package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IStatusDao;
import com.OfficeManager.app.entities.Status;
import com.OfficeManager.app.services.interfaces.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("statusService")
@Transactional
public class StatusServiceImpl implements IStatusService {

    @Autowired
    private IStatusDao statusDao;

    @Override
    public List<Status> fetchAll() {
        return statusDao.findAll();
    }

    @Override
    public Status saveStatus(Status status) {
        return statusDao.save(status);
    }

    @Override
    public Optional<Status> findById(int id) { return statusDao.findById(id); }

    @Override
    public Status findByName(String name) {
        return statusDao.findByName(name);
    }
}
