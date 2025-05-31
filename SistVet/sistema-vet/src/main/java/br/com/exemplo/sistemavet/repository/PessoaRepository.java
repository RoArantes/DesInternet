package br.com.exemplo.sistemavet.repository;

import br.com.exemplo.sistemavet.model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {
    // Métodos de consulta personalizados, se necessário
}