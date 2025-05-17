package org.example.controller;

import org.example.constant.Constant;
import org.example.model.Usuario;
import org.example.UsuarioRepository.UsuarioRepository; // Para simplificar, usando direto o repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para controle de acesso baseado em roles
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constant.API_USUARIOS)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Para atualizar senha

    // Criar Usuário (geralmente o registro é feito no AuthController)
    // Este endpoint pode ser para um admin criar outros usuários
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Somente ADMIN pode criar usuários diretamente aqui
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email já existe
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // Listar todos os Usuários
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar Usuário por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @usuarioSecurityService.hasUserId(authentication, #id)") // Admin ou o próprio usuário
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar Usuário
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @usuarioSecurityService.hasUserId(authentication, #id)")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String id, @RequestBody Usuario dadosUsuario) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setEmail(dadosUsuario.getEmail()); // Cuidado com alteração de email se for usado como username
                    if (dadosUsuario.getSenha() != null && !dadosUsuario.getSenha().isEmpty()) {
                        usuarioExistente.setSenha(passwordEncoder.encode(dadosUsuario.getSenha()));
                    }
                    // Outros campos a serem atualizados
                    Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioAtualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar Usuário
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}