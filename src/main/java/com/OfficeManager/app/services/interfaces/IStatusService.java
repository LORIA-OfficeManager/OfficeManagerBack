package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.entities.Status;

import java.util.List;

public interface IStatusService {

    public List<Status> fetchAll();

    public Status saveStatus(Status status);
}
