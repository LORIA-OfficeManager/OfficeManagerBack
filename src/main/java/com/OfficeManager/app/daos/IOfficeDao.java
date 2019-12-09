package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOfficeDao extends JpaRepository<Office, Integer> {

}
