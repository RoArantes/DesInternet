package org.example.service;

import org.example.model.Usuario;
import org.example.UsuarioRepository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Para GrantedAuthority
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado com o email: " + email)
                );

        // Por enquanto, vamos atribuir uma role padrão. Você pode expandir isso.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(usuario.getEmail(), usuario.getSenha(), authorities);
    }
}