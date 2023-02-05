package com.api.senati.Service;

import com.api.senati.DTO.GrupoDTO;
import com.api.senati.DTO.TipoDocDTO;
import com.api.senati.Entity.Grupo;
import com.api.senati.Entity.TipoDocs;
import com.api.senati.Repository.TipoDocsRepository;
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
public class TipoDocServiceImpl implements TipoDocService{
    @Autowired
    TipoDocsRepository tipoDocsRepository;
    @Autowired
    private Validator validator;
    @Override
    public List<TipoDocs> listarTipoDocs() {
        return tipoDocsRepository.findAll();
    }

    @Override
    public TipoDocs obtenerTipoDoc(Integer idTipoDoc) {
        return tipoDocsRepository.findByIdTipo(idTipoDoc).orElse(null);
    }

    @Override
    public TipoDocs crearTipoDoc(TipoDocs tipoDocs) {
        Set<ConstraintViolation<TipoDocs>> violations = validator.validate(tipoDocs);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        tipoDocs.setModificado(Timestamp.from(Instant.now()));
        tipoDocs.setRegistro(Timestamp.from(Instant.now()));
        return tipoDocsRepository.save(tipoDocs);
    }

    @Override
    public TipoDocs editarTipoDoc(Integer idTipoDoc, TipoDocDTO tipoDocDTO, Integer idUserLog) {
        TipoDocs tipoDocs = tipoDocsRepository.findByIdTipo(idTipoDoc).orElse(null);
        if (tipoDocs == null) {
            return null;
        }
        Set<ConstraintViolation<TipoDocDTO>> violations = validator.validate(tipoDocDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        tipoDocs.setModificado(Timestamp.from(Instant.now()));
        tipoDocs.setModpor(idUserLog);
        if (tipoDocDTO.getTipo() != null && !tipoDocDTO.getTipo().isEmpty())
            tipoDocs.setTipo(tipoDocDTO.getTipo());
        if (tipoDocDTO.getObservacion() != null && !tipoDocDTO.getObservacion().isEmpty())
            tipoDocs.setObservacion(tipoDocDTO.getObservacion());
        return tipoDocsRepository.save(tipoDocs);
    }

    @Override
    public boolean eliminarTipoDoc(Integer idTipoDoc, Integer idUserLog) {
        TipoDocs tipoDocs = tipoDocsRepository.findByIdTipo(idTipoDoc).orElse(null);
        if (tipoDocs == null) {
            return false;
        }
        tipoDocs.setEstado("0");
        tipoDocs.setModpor(idUserLog);
        tipoDocs.setModificado(Timestamp.from(Instant.now()));
        tipoDocs.setObservacion("Tipo de documento eliminado");
        tipoDocsRepository.save(tipoDocs);
        return true;
    }
}
