package com.OfficeManager.app.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImportService {
    public String importBureau(MultipartFile loriatab) throws IOException;

    public String importAffectation(MultipartFile loriatab) throws IOException;

}
