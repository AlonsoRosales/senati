package com.api.senati.Service;

import com.api.senati.DTO.FirmanteDTO;
import com.api.senati.Entity.Firmante;

import java.util.List;

public interface FirmanteService {
    public List<Firmante> findByIdDependencia(Integer idDependencia);
    public Firmante findByIdDependenciaAndIdFirmante(Integer idDependencia, Integer idFirmante);
    public Firmante createFirmante(Firmante firmante);
    public Firmante actualizarFirmante(Integer idFirmante,FirmanteDTO firmanteDTO);
}
