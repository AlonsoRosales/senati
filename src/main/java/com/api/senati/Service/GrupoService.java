package com.api.senati.Service;

import com.api.senati.DTO.GrupoDTO;
import com.api.senati.Entity.Grupo;

import java.util.List;

public interface GrupoService {
    public List<Grupo> obtenerListaGrupos();
    public Grupo obtenerGrupo(Integer idGrupo);
    public Grupo crearGrupo(Grupo grupo);
    public Grupo editarGrupo(Integer idGrupo, GrupoDTO grupoDTO, Integer idUserLog);
    public boolean eliminarGrupo(Integer idGrupo, Integer idUserLog);
}
