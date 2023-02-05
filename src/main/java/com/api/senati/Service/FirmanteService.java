package com.api.senati.Service;

import com.api.senati.Config.CustomException;
import com.api.senati.DTO.FirmanteDTO;
import com.api.senati.Entity.Firmante;

import java.util.List;

public interface FirmanteService {
    public List<Firmante> findByIdDependencia(Integer idDependencia);
    public Firmante findByIdDependenciaAndIdFirmante(Integer idDependencia, Integer idFirmante);
    public Firmante createFirmante(Firmante firmante) throws CustomException;
    public Firmante actualizarFirmante(Integer idFirmante,FirmanteDTO firmanteDTO);
    public boolean eliminarFirmante(Integer idFirmante, Integer idUser);
}
