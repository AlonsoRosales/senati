package com.api.senati.Repository;

import com.api.senati.Entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    @Query(value = "SELECT * FROM cloud_grupo WHERE gr_estado='1'", nativeQuery = true)
    List<Grupo> findAll();
    Optional<Grupo> findByIdGrupo(Integer idGrupo);
}