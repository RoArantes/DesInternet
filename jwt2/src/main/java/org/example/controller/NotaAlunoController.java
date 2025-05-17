package org.example.controller;

import org.example.constant.Constant;
import org.example.model.NotaAluno;
import org.example.UsuarioRepository.NotaAlunoRepository; // Para simplificar
import org.example.security.JwtTokenProvider; // Para pegar o usuário do token
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.server.ResponseStatusException; // Se não usar ResponseEntity

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constant.API_NOTAS_ALUNOS)
public class NotaAlunoController {

    @Autowired
    private NotaAlunoRepository notaAlunoRepository;

    @Autowired
    private JwtTokenProvider tokenProvider; // Para extrair o email do usuário do token se necessário

    // Criar NotaAluno
    @PostMapping
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode criar nota
    public ResponseEntity<NotaAluno> criarNota(@RequestBody NotaAluno notaAluno, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        notaAluno.setEmailUsuario(userPrincipal.getUsername()); // Associa a nota ao usuário logado
        notaAluno.calcularMedia(); // Garante que a média seja calculada
        NotaAluno novaNota = notaAlunoRepository.save(notaAluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }

    // Listar todas as notas do usuário logado
    @GetMapping("/minhas-notas")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotaAluno>> listarMinhasNotas(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        List<NotaAluno> notas = notaAlunoRepository.findByEmailUsuario(userPrincipal.getUsername());
        return ResponseEntity.ok(notas);
    }

    // Listar todas as notas (somente ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NotaAluno>> listarTodasAsNotas() {
        List<NotaAluno> notas = notaAlunoRepository.findAll();
        return ResponseEntity.ok(notas);
    }


    // Buscar NotaAluno por ID
    // Um usuário só pode buscar sua própria nota, ou um admin pode buscar qualquer nota.
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotaAluno> buscarNotaPorId(@PathVariable String id, Authentication authentication) {
        Optional<NotaAluno> notaOpt = notaAlunoRepository.findById(id);
        if (notaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        NotaAluno nota = notaOpt.get();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Verifica se o usuário é admin ou se a nota pertence a ele
        if (userPrincipal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ||
                nota.getEmailUsuario().equals(userPrincipal.getUsername())) {
            return ResponseEntity.ok(nota);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Não autorizado
        }
    }

    // Atualizar NotaAluno
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotaAluno> atualizarNota(@PathVariable String id, @RequestBody NotaAluno dadosNota, Authentication authentication) {
        Optional<NotaAluno> notaExistenteOpt = notaAlunoRepository.findById(id);
        if (notaExistenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        NotaAluno notaExistente = notaExistenteOpt.get();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Somente o dono da nota ou um admin pode atualizar
        if (!notaExistente.getEmailUsuario().equals(userPrincipal.getUsername()) &&
                !userPrincipal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        notaExistente.setNome(dadosNota.getNome());
        notaExistente.setNota1(dadosNota.getNota1());
        notaExistente.setNota2(dadosNota.getNota2());
        // O emailUsuario não deve ser alterado aqui, a menos que seja uma funcionalidade específica.
        // notaExistente.setEmailUsuario(dadosNota.getEmailUsuario());
        notaExistente.calcularMedia(); // Recalcula a média

        NotaAluno notaAtualizada = notaAlunoRepository.save(notaExistente);
        return ResponseEntity.ok(notaAtualizada);
    }

    // Deletar NotaAluno
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletarNota(@PathVariable String id, Authentication authentication) {
        Optional<NotaAluno> notaExistenteOpt = notaAlunoRepository.findById(id);
        if (notaExistenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        NotaAluno notaExistente = notaExistenteOpt.get();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Somente o dono da nota ou um admin pode deletar
        if (!notaExistente.getEmailUsuario().equals(userPrincipal.getUsername()) &&
                !userPrincipal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        notaAlunoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}