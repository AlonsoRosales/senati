package com.api.senati.Repository;

import com.api.senati.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {
Optional<Usuario> findByUsuario(String username);
List<Usuario> findAllByEstado(String estado);
Optional<Usuario> findByIdUsuario(Integer idUsuario);
}