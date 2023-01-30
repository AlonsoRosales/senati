package com.api.senati.Security;

import com.api.senati.Entity.Usuario;
import com.api.senati.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Usuario usuario= userRepository.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("El usuario con username "+username+" no existe."));
        System.out.println(usuario.getUsuario());
        return new UserDetailsImpl(usuario);

    }
}
