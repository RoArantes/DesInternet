package org.example.UsuarioRepository;

import org.example.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email); // Método para buscar usuário por email
    Boolean existsByEmail(String email);
}