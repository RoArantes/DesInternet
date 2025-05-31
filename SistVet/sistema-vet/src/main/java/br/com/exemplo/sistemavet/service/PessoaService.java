package br.com.exemplo.sistemavet.service;

import br.com.exemplo.sistemavet.model.Pessoa;
import br.com.exemplo.sistemavet.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getPessoaById(String id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa createPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa updatePessoa(String id, Pessoa pessoaDetails) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com o id: " + id));
        pessoa.setNome(pessoaDetails.getNome());
        pessoa.setCpf(pessoaDetails.getCpf());
        pessoa.setTelefone(pessoaDetails.getTelefone());
        pessoa.setEndereco(pessoaDetails.getEndereco());
        return pessoaRepository.save(pessoa);
    }

    public void deletePessoa(String id) {
        pessoaRepository.deleteById(id);
    }
}