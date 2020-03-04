package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Team;

import java.util.List;
import java.util.Optional;

public interface ITeamService {

    public List<Team> fetchAll();

    public Optional<Team> findById(int id);

    public Optional<Team> findByName(String name);

    public void deleteById(Integer id);

    public Team saveTeam(Team team);

    public void switchPersonTODefaultTeam(int idD, int idT);
}
