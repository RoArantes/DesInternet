package org.example.UsuarioRepository;

import org.example.model.NotaAluno;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotaAlunoRepository extends MongoRepository<NotaAluno, String> {
    List<NotaAluno> findByEmailUsuario(String emailUsuario); // Buscar notas por email do usuário
}