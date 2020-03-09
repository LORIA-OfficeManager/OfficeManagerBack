package com.OfficeManager.app.services.interfaces;

import com.OfficeManager.app.dtos.MessageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImportService {
    public MessageDto importBureau(MultipartFile loriatab) throws IOException;

    public MessageDto importAffectation(MultipartFile loriatab, boolean wipe) throws IOException;

}
