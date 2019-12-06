package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.ITeamDao;
import com.OfficeManager.app.entities.Team;
import com.OfficeManager.app.services.interfaces.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("teamService")
@Transactional
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private ITeamDao teamDao;

    @Override
    public List<Team> fetchAll() {
        return teamDao.findAll();
    }
}
