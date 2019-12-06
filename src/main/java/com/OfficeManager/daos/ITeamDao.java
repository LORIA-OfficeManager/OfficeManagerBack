package com.OfficeManager.daos;

import com.OfficeManager.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITeamDao extends JpaRepository<Team, Integer> {

    @Query("SELECT * "
            + "FROM TEAM "
    )
    public List<Team> fetchAll();
}
