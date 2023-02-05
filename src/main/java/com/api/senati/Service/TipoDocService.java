package com.api.senati.Service;

import com.api.senati.DTO.TipoDocDTO;
import com.api.senati.Entity.TipoDocs;

import java.util.List;

public interface TipoDocService {
    public List<TipoDocs> listarTipoDocs();
    public TipoDocs obtenerTipoDoc(Integer idTipoDoc);
    public TipoDocs crearTipoDoc(TipoDocs tipoDocs);
    public TipoDocs editarTipoDoc(Integer idTipoDoc, TipoDocDTO tipoDocDTO, Integer idUserLog);
    public boolean eliminarTipoDoc(Integer idTipoDoc, Integer idUserLog);
}
