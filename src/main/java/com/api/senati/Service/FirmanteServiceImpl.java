package com.api.senati.Service;

import com.api.senati.DTO.FirmanteDTO;
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
public class FirmanteServiceImpl implements FirmanteService {
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
        return firmanteRepository.findFirmanteByIdDependenciaAndIdFirmante(idDependencia, idFirmante).orElse(null);
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

    @Override
    public Firmante actualizarFirmante(Integer idFirmante, FirmanteDTO firmanteDTO) {
        System.out.println(idFirmante);
        Firmante firmante = firmanteRepository.findByIdFirmante(idFirmante).orElse(null);
        if (firmante == null) {
            return null;
        }
        Set<ConstraintViolation<FirmanteDTO>> violations = validator.validate(firmanteDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        firmante.setModificado(Timestamp.from(Instant.now()));
        firmante.setModpor(firmanteDTO.getModpor());
        if (firmanteDTO.getDocide() != null && !firmanteDTO.getDocide().isEmpty())
            firmante.setDocide(firmanteDTO.getDocide());
        if (firmanteDTO.getNombre() != null && !firmanteDTO.getNombre().isEmpty())
            firmante.setNombre(firmanteDTO.getNombre());
        if (firmanteDTO.getCorreo() != null && !firmanteDTO.getCorreo().isEmpty())
            firmante.setCorreo(firmanteDTO.getCorreo());
        if (firmanteDTO.getCorreoA() != null && !firmanteDTO.getCorreoA().isEmpty())
            firmante.setCorreoA(firmanteDTO.getCorreoA());
        if (firmanteDTO.getRepositorio() != null && !firmanteDTO.getRepositorio().isEmpty())
            firmante.setRepositorio(firmanteDTO.getRepositorio());
        if (firmanteDTO.getFirma() != null && !firmanteDTO.getFirma().isEmpty())
            firmante.setFirma(firmanteDTO.getFirma());
        if (firmanteDTO.getPassword() != null && !firmanteDTO.getPassword().isEmpty())
            firmante.setPassword(firmanteDTO.getPassword());
        if (firmanteDTO.getEstado() != null && !firmanteDTO.getEstado().isEmpty())
            firmante.setEstado(firmanteDTO.getEstado());
        if (firmanteDTO.getCargo() != null && !firmanteDTO.getCargo().isEmpty())
            firmante.setCargo(firmanteDTO.getCargo());
        if (firmanteDTO.getRol() != null && !firmanteDTO.getRol().isEmpty()) firmante.setRol(firmanteDTO.getRol());
        return firmanteRepository.save(firmante);
    }
}
