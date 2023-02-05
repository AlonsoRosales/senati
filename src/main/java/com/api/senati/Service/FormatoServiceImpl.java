package com.api.senati.Service;

import com.api.senati.DTO.FirmanteDTO;
import com.api.senati.DTO.FormatoDTO;
import com.api.senati.Entity.Firmante;
import com.api.senati.Entity.Formato;
import com.api.senati.Repository.FormatoRepository;
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

public class FormatoServiceImpl implements FormatoService{
    @Autowired
    FormatoRepository formatoRepository;
    @Autowired
    private Validator validator;
    @Override
    public Formato crearFormato(Formato formato) {
        Set<ConstraintViolation<Formato>> violations = validator.validate(formato);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        formato.setModificado(Timestamp.from(Instant.now()));
        formato.setRegistro(Timestamp.from(Instant.now()));
        formato.setTipo("0");
        return formatoRepository.save(formato);
    }

    @Override
    public List<Formato> obtenerListaDeFormatos() {
        return formatoRepository.findAll();
    }

    @Override
    public Formato obtenerFormato(Integer id) {
        return formatoRepository.findByIdFormato(id).orElse(null);
    }

    @Override
    public Formato editarFormato(Integer id, FormatoDTO formatoDTO, Integer idUserLog) {
        Formato formato = formatoRepository.findByIdFormato(id).orElse(null);
        if (formato == null) {
            return null;
        }
        Set<ConstraintViolation<FormatoDTO>> violations = validator.validate(formatoDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        formato.setModificado(Timestamp.from(Instant.now()));
        formato.setModpor(idUserLog);
        if (formatoDTO.getNombre() != null && !formatoDTO.getNombre().isEmpty())
            formato.setNombre(formatoDTO.getNombre());
        if (formatoDTO.getDescripcion() != null && !formatoDTO.getDescripcion().isEmpty())
            formato.setDescripcion(formatoDTO.getDescripcion());
        if (formatoDTO.getObservacion() != null && !formatoDTO.getObservacion().isEmpty())
            formato.setObservacion(formatoDTO.getObservacion());
        if (formatoDTO.getAlto() != null)
            formato.setAlto(formatoDTO.getAlto());
        if (formatoDTO.getAncho() != null)
            formato.setAncho(formatoDTO.getAncho());
        if (formatoDTO.getTipo() != null && !formatoDTO.getTipo().isEmpty())
            formato.setTipo(formatoDTO.getTipo());
        return formato;
    }

    @Override
    public boolean eliminarFormato(Integer idFormato, Integer idUserLog) {
        Formato formato = formatoRepository.findByIdFormato(idFormato).orElse(null);
        if (formato == null) {
            return false;
        }
        formato.setEstado("0");
        formato.setModpor(idUserLog);
        formato.setModificado(Timestamp.from(Instant.now()));
        formato.setObservacion("Formato eliminado");
        formatoRepository.save(formato);
        return true;
    }
}
