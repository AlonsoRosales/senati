package com.api.senati.Service;

import com.api.senati.Entity.DataDocuments;

public interface DocumentService {
    public String crearDocumento(DataDocuments documents,Integer tipo) throws Exception;
}
