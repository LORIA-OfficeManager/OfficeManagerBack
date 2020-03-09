package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.ITeamDao;
import com.OfficeManager.app.entities.Team;
import com.OfficeManager.app.services.interfaces.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("teamService")
@Transactional
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private ITeamDao teamDao;

    @Override
    public List<Team> fetchAll() {
        return teamDao.findAll();
    }

    @Override
    public Optional<Team> findById(int id) { return teamDao.findById(id); }

    @Override
    public Optional<Team> findByName(String name) {
        return teamDao.findByName(name);
    }

    @Override
    public void deleteById(Integer id) { teamDao.deleteById(id); }

    @Override
    public Team saveTeam(Team team) {
        return teamDao.save(team);
    }

    @Override
    public void switchPersonTODefaultTeam(int idD, int idT) {
        teamDao.switcPersonToDefaultTeam(idD, idT);
    }

    @Override
    public boolean isAuthorisedName(String name) {
        return !teamDao.existsByName(name) && !name.equals(DepartmentServiceImpl.DEFAULT_DEP);
    }
}
