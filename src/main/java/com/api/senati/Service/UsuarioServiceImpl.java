package com.api.senati.Service;

import com.api.senati.DTO.UsuarioDTO;
import com.api.senati.Entity.Firmante;
import com.api.senati.Entity.Usuario;
import com.api.senati.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    UserRepository usuarioRepository;
    @Autowired
    private Validator validator;
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        usuario.setDatimod(Timestamp.from(Instant.now()));
        usuario.setDatireg(Timestamp.from(Instant.now()));
        usuario.setVigencia(Date.from(Instant.now()));
        usuario.setEstado("1");
        String pwd = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(10));
        usuario.setPassword(pwd);
        Usuario usuario1 =usuarioRepository.save(usuario);
        usuario1.setPassword("***********");
        return usuario1;
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios =usuarioRepository.findAllByEstado("1");
        for (Usuario usuario:usuarios){
            usuario.setPassword("**********");
        }
        return usuarios;
    }

    @Override
    public Usuario obetenerUsuario(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario).orElse(null);
    }

    @Override
    public Usuario editarUsuario(Integer idUsuario, UsuarioDTO usuario, Integer idUserLog) {
        Usuario usuario1 = usuarioRepository.findByIdUsuario(idUsuario).orElse(null);
        if( usuario1==null) return  null;
        if (usuario.getUsuario() != null && !usuario.getUsuario().isEmpty())
            usuario1.setUsuario(usuario.getUsuario());
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()){
            String pwd = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(10));
            usuario1.setPassword(pwd);
        }
        if (usuario.getNivel() != null && !usuario.getNivel().isEmpty())
            usuario1.setNivel(usuario.getNivel());
        if (usuario.getVigencia() != null)
            usuario1.setVigencia(usuario.getVigencia());
        if (usuario.getObservacion() != null && !usuario.getObservacion().isEmpty())
            usuario1.setObservacion(usuario.getObservacion());
        if (usuario.getEstado() != null && !usuario.getEstado().isEmpty())
            usuario1.setEstado(usuario.getEstado());
        usuario1.setModpor(idUserLog);
        usuario1.setDatimod(Timestamp.from(Instant.now()));
        usuarioRepository.save(usuario1);
        usuario1.setPassword("**********");
        return usuario1;
    }

    @Override
    public boolean eliminarUsuario(Integer idUsuario, Integer idUserLog) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario).orElse(null);
        if( usuario==null) return  false;
        usuario.setModpor(idUserLog);
        usuario.setEstado("0");
        usuario.setDatimod(Timestamp.from(Instant.now()));
        usuario.setObservacion("Usuario eliminado");
        usuarioRepository.save(usuario);
        return true;
    }
}
