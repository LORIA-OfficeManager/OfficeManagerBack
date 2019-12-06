package com.OfficeManager.services.impl;

import com.OfficeManager.daos.ITeamDao;
import com.OfficeManager.entities.Team;
import com.OfficeManager.services.interfaces.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("officeService")
@Transactional
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private ITeamDao teamDao;

    @Override
    public List<Team> fetchAll() {
        return teamDao.fetchAll();
    }
}
