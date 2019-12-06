package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IStatusDao;
import com.OfficeManager.app.entities.Status;
import com.OfficeManager.app.services.interfaces.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("statusService")
@Transactional
public class StatusServiceImpl implements IStatusService {

    @Autowired
    private IStatusDao statusDao;

    @Override
    public List<Status> fetchAll() {
        return statusDao.findAll();
    }
}
