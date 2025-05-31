package br.com.exemplo.sistemavet.repository;

import br.com.exemplo.sistemavet.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    List<Animal> findByPessoaId(String pessoaId); // Exemplo de consulta personalizada
    List<Animal> findByEspecie(String especie); // Exemplo de consulta personalizada
}