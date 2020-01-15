package com.OfficeManager.app.services.interfaces;

import java.io.IOException;

public interface IImportService {

    public String importAffectation(String path) throws IOException;

    public String importBureau(String path) throws IOException;

}
