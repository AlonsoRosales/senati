package com.api.senati.Service;

import com.api.senati.DTO.UsuarioDTO;
import com.api.senati.Entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UsuarioService {
    public Usuario crearUsuario(Usuario usuario);
    public List<Usuario> obtenerUsuarios();
    public Usuario obetenerUsuario(Integer idUsuario);
    public Usuario editarUsuario(Integer idUsuario, UsuarioDTO usuario,Integer idUserLog);
    public boolean eliminarUsuario(Integer idUsuario,Integer idUserLog);

}
