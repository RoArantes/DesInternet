package org.example.service;

import org.example.model.Usuario;
import org.example.UsuarioRepository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Erro: Email já está em uso!"); // Ou uma exceção customizada
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> atualizarUsuario(String id, Usuario dadosUsuario) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setEmail(dadosUsuario.getEmail());
                    if (dadosUsuario.getSenha() != null && !dadosUsuario.getSenha().isEmpty()) {
                        usuarioExistente.setSenha(passwordEncoder.encode(dadosUsuario.getSenha()));
                    }
                    return usuarioRepository.save(usuarioExistente);
                });
    }

    public boolean deletarUsuario(String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}