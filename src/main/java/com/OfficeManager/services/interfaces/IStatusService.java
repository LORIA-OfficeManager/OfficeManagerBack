package com.OfficeManager.services.interfaces;

import com.OfficeManager.entities.Status;

import java.util.List;

public interface IStatusService {

    public List<Status> fetchAll();
}
