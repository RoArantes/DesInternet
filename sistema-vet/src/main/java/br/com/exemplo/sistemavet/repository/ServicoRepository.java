package br.com.exemplo.sistemavet.repository;

import br.com.exemplo.sistemavet.model.Servico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ServicoRepository extends MongoRepository<Servico, String> {
    List<Servico> findByAnimalId(String animalId);
    // Adicione outros métodos find conforme necessário, ex: por intervalo de datas, tipo, status
}