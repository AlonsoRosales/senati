package com.api.senati.Service;

import com.api.senati.DTO.FormatoDTO;
import com.api.senati.Entity.Formato;

import java.util.List;

public interface FormatoService {
    public Formato crearFormato(Formato formato);
    public List<Formato> obtenerListaDeFormatos();
    public Formato obtenerFormato(Integer id);
    public Formato editarFormato(Integer id, FormatoDTO formatoDTO, Integer idUserLog);
    public boolean eliminarFormato(Integer idFormato, Integer idUserLog);
}
