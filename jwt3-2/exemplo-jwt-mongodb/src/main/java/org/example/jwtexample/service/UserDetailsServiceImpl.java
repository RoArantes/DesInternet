package org.example.jwtexample.service;

import org.example.jwtexample.model.mysql.User;
import org.example.jwtexample.repository.mysql.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Para UserDetails padrão (sem roles específicos)

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome: " + username));

        // Se você tivesse roles (perfis), você os carregaria aqui
        // Por exemplo, se 'user.getRoles()' retornasse "ROLE_USER,ROLE_ADMIN"
        // List<SimpleGrantedAuthority> authorities = Arrays.stream(user.getRoles().split(","))
        // .map(SimpleGrantedAuthority::new)
        // .collect(Collectors.toList());
        // return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        // Para este exemplo simples, sem roles complexos:
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // Lista vazia de authorities (permissões)
        );
    }
}