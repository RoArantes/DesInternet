package org.example.controller;

import org.example.constant.Constant;
import org.example.dto.LoginRequest;
import org.example.dto.TokenResponse;
import org.example.model.Usuario;
import org.example.UsuarioRepository.UsuarioRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.AUTH_URL) // Define um prefixo para os endpoints de autenticação
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login") // Constant.API_LOGIN
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new TokenResponse(jwt, "Login bem-sucedido!"));
    }

    @PostMapping("/register") // Constant.API_REGISTER
    public ResponseEntity<?> registerUser(@RequestBody Usuario signUpRequest) { // Usando a classe Usuario diretamente para registro
        if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("Email já está em uso!", HttpStatus.BAD_REQUEST);
        }

        // Criando a conta do usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(signUpRequest.getSenha())); // Criptografa a senha

        // Definir roles aqui se necessário. Por simplicidade, não estamos adicionando roles complexas.
        // Ex: usuario.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(RoleName.ROLE_USER))));

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
    }
}