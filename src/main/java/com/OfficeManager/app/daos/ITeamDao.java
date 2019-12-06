package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITeamDao extends JpaRepository<Team, Integer> {

}
