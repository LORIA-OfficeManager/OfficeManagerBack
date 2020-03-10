package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ITeamDao extends JpaRepository<Team, Integer> {
    @Query (
            value = "SELECT * FROM team t WHERE t.name = :name",
            nativeQuery = true
    )
    Optional<Team> findByName(@Param("name") String name);

    @Modifying
    @Query ("UPDATE Person p SET p.team = (SELECT t FROM Team t WHERE t.name = (SELECT d.name FROM Department d WHERE d.id = :idD)) WHERE p.team.id = :idT")
    void switcPersonToDefaultTeam(@Param("idD") int idD, @Param("idT") int idT);

    @Query(
            value = "SELECT (count(t) > 0) FROM Team t WHERE t.name = :name"
    )
    boolean existsByName(String name);
}
