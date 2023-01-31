package com.api.senati.Service;

import com.api.senati.Entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {
    public Usuario crearUsuario(Usuario usuario);
    public List<Usuario> obtenerUsuarios(Usuario usuario);
    public Usuario obetenerUsuario(Integer idUsuario);
    public Usuario editarUsuario(Integer idUsuario, Usuario usuario);
    public void eliminarUsuario(Integer idUsuario);

}
