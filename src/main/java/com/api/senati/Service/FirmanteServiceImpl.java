package com.api.senati.Service;

import com.api.senati.Entity.Firmante;
import com.api.senati.Repository.FirmanteRepository;
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
public class FirmanteServiceImpl implements FirmanteService{
    @Autowired
    FirmanteRepository firmanteRepository;
    @Autowired
    private Validator validator;

    @Override
    public List<Firmante> findByIdDependencia(Integer idDependencia) {
        return firmanteRepository.findAllByIdDependencia(idDependencia);
    }

    @Override
    public Firmante findByIdDependenciaAndIdFirmante(Integer idDependencia, Integer idFirmante) {
        return firmanteRepository.findFirmanteByIdDependenciaAndIdFirmante(idDependencia,idFirmante).orElse(null);
    }

    @Override
    public Firmante createFirmante(Firmante firmante) {
        Set<ConstraintViolation<Firmante>> violations = validator.validate(firmante);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        firmante.setModificado(Timestamp.from(Instant.now()));
        firmante.setRegistro(Timestamp.from(Instant.now()));
        firmante.setEstado("1");
        return firmanteRepository.save(firmante);
    }
}
