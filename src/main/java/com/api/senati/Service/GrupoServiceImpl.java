package com.api.senati.Service;

import com.api.senati.DTO.GrupoDTO;
import com.api.senati.Entity.Grupo;
import com.api.senati.Repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class GrupoServiceImpl implements GrupoService {
    @Autowired
    GrupoRepository grupoRepository;
    @Autowired
    private Validator validator;

    @Override
    public List<Grupo> obtenerListaGrupos() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo obtenerGrupo(Integer idGrupo) {
        return grupoRepository.findByIdGrupo(idGrupo).orElse(null);
    }

    @Override
    public Grupo crearGrupo(Grupo grupo) {
        Set<ConstraintViolation<Grupo>> violations = validator.validate(grupo);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        grupo.setModificado(Timestamp.from(Instant.now()));
        grupo.setRegistro(Timestamp.from(Instant.now()));
        return grupoRepository.save(grupo);
    }

    @Override
    public Grupo editarGrupo(Integer idGrupo, GrupoDTO grupoDTO, Integer idUserLog) {
        Grupo grupo = grupoRepository.findByIdGrupo(idGrupo).orElse(null);
        if (grupo == null) {
            return null;
        }
        Set<ConstraintViolation<GrupoDTO>> violations = validator.validate(grupoDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        grupo.setModificado(Timestamp.from(Instant.now()));
        grupo.setModpor(idUserLog);
        if (grupoDTO.getNombre() != null && !grupoDTO.getNombre().isEmpty())
            grupo.setNombre(grupoDTO.getNombre());
        if (grupoDTO.getObservacion() != null && !grupoDTO.getObservacion().isEmpty())
            grupo.setObservacion(grupoDTO.getObservacion());
        if (grupoDTO.getDescripcion() != null && !grupoDTO.getDescripcion().isEmpty())
            grupo.setDescripcion(grupoDTO.getDescripcion());
        if (grupoDTO.getUrlPlantilla() != null && !grupoDTO.getUrlPlantilla().isEmpty())
            grupo.setUrlPlantilla(grupoDTO.getUrlPlantilla());
        if (grupoDTO.getIdTipo() != null)
            grupo.setIdGrupo(grupoDTO.getIdTipo());
        return grupoRepository.save(grupo);
    }

    @Override
    public boolean eliminarGrupo(Integer idGrupo, Integer idUserLog) {
        Grupo grupo = grupoRepository.findByIdGrupo(idGrupo).orElse(null);
        if (grupo == null) {
            return false;
        }
        grupo.setEstado("0");
        grupo.setModpor(idUserLog);
        grupo.setModificado(Timestamp.from(Instant.now()));
        grupo.setObservacion("Grupo eliminado");
        grupoRepository.save(grupo);
        return true;
    }
}
