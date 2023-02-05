package com.api.senati.Repository;

import com.api.senati.Entity.Firmante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FirmanteRepository extends JpaRepository<Firmante, Integer> {
    @Query(value = "SELECT * FROM cloud_firmante WHERE fi_iddependencia = ?1 AND fi_estado <> '0' AND fi_rol = '1'", nativeQuery = true)
    List<Firmante> findAllByIdDependencia(Integer idDependencia);
    @Query(value = "SELECT * FROM cloud_firmante WHERE fi_idfirmante = ?1 AND fi_estado <> '0'", nativeQuery = true)
    Optional<Firmante> findByIdFirmante(Integer idFirmante);

    @Query(value = "SELECT * FROM cloud_firmante WHERE fi_iddependencia = ?1 AND fi_estado <> '0' AND fi_rol = '1' AND fi_idfirmante = ?2", nativeQuery = true)
    Optional<Firmante> findFirmanteByIdDependenciaAndIdFirmante(Integer idDependencia, Integer idFirmante);
    @Query(value = "SELECT * FROM cloud_firmante WHERE fi_docide =?1", nativeQuery = true)
    Optional<Firmante> findByDocide(String docide);
}