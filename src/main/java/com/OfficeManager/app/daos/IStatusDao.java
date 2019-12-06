package com.OfficeManager.app.daos;

import com.OfficeManager.app.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStatusDao extends JpaRepository<Status, Integer> {

}
