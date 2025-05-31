package br.com.exemplo.sistemavet.controller;

import br.com.exemplo.sistemavet.config.JwtUtil;
import br.com.exemplo.sistemavet.model.User; // Supondo um modelo User para registro
import br.com.exemplo.sistemavet.repository.UserRepository; // Para registro
import br.com.exemplo.sistemavet.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder; // Para registro
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository; // Para registro de usuário

    @Autowired
    private PasswordEncoder passwordEncoder; // Para registro de usuário

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.username).isPresent()) {
            return ResponseEntity.badRequest().body("Nome de usuário já existente!");
        }
        User newUser = new User(registrationRequest.username, passwordEncoder.encode(registrationRequest.password));
        userRepository.save(newUser);
        return ResponseEntity.ok("Usuário registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Nome de usuário ou senha incorretos", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username);
        final String jwt = jwtUtil.generateToken(userDetails); // Gerar JWT [cite: 3]
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}