package br.com.exemplo.sistemavet.service;

import br.com.exemplo.sistemavet.model.User;
import br.com.exemplo.sistemavet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Ou use authorities se implementar papéis (roles)

@Service
public class CustomUserDetailsService {//implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

  /*  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username));
        // Para o User do Spring Security, papéis/autoridades seriam tratados aqui
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }*/
}