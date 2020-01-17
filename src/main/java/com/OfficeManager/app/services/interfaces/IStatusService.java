package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Status;

import java.util.List;
import java.util.Optional;

public interface IStatusService {

    public List<Status> fetchAll();

    public Status saveStatus(Status status);

    public Optional<Status> findById(int id);

    public Status findByName(String name);
}
