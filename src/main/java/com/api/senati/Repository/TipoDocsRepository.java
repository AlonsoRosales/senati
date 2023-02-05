package com.api.senati.Repository;

import com.api.senati.Entity.TipoDocs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoDocsRepository extends JpaRepository<TipoDocs, Integer> {
    @Query(value = "SELECT * FROM cloud_tipo WHERE ti_estado='1'", nativeQuery = true)
    List<TipoDocs> findAll();
    Optional<TipoDocs> findByIdTipo(Integer idTipo);
}