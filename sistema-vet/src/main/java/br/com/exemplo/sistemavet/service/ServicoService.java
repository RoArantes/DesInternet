package br.com.exemplo.sistemavet.service;

import br.com.exemplo.sistemavet.model.Servico;
import br.com.exemplo.sistemavet.repository.AnimalRepository; // Para validar animalId
import br.com.exemplo.sistemavet.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AnimalRepository animalRepository; // Para validar se o animal existe

    public List<Servico> getAllServicos() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> getServicoById(String id) {
        return servicoRepository.findById(id);
    }

    public List<Servico> getServicosByAnimalId(String animalId) {
        return servicoRepository.findByAnimalId(animalId);
    }

    public Servico createServico(Servico servico) {
        // Validar se animalId existe [cite: 13]
        if (!animalRepository.existsById(servico.getAnimalId())) {
            throw new RuntimeException("Animal não encontrado para registro do serviço com id: " + servico.getAnimalId());
        }
        return servicoRepository.save(servico);
    }

    public Servico updateServico(String id, Servico servicoDetails) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o id: " + id));

        // Validar animalId se estiver sendo alterado
        if (!servicoDetails.getAnimalId().equals(servico.getAnimalId())) {
            if (!animalRepository.existsById(servicoDetails.getAnimalId())) {
                throw new RuntimeException("Novo Animal não encontrado com o id: " + servicoDetails.getAnimalId());
            }
        }
        servico.setData(servicoDetails.getData());
        servico.setTipo(servicoDetails.getTipo());
        servico.setValor(servicoDetails.getValor());
        servico.setStatus(servicoDetails.getStatus());
        servico.setAnimalId(servicoDetails.getAnimalId());
        return servicoRepository.save(servico);
    }

    public void deleteServico(String id) {
        servicoRepository.deleteById(id);
    }
}