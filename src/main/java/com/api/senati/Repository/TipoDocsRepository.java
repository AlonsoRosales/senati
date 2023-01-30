package com.api.senati.Repository;

import com.api.senati.Entity.TipoDocs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocsRepository extends JpaRepository<TipoDocs, Integer> {
}