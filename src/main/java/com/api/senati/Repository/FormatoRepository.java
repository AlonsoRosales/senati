package com.api.senati.Repository;

import com.api.senati.Entity.Formato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormatoRepository extends JpaRepository<Formato, Integer> {
    Optional<Formato> findByIdFormato(Integer idFormato);
}