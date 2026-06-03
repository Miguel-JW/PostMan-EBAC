package com.example.service;

import com.example.model.Nome;
import com.example.repository.NomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NomeService {

    private final NomeRepository nomeRepository;

    // ── INSERT ──────────────────────────────────────────────
    public Nome inserir(String nome) {
        Nome entidade = new Nome();
        entidade.setNome(nome);
        return nomeRepository.save(entidade);
    }

    // ── UPDATE (todos os registros com nomeAntigo) ───────────
    @Transactional
    public int atualizar(String nomeAntigo, String nomeNovo) {
        int afetados = nomeRepository.updateAllByNome(nomeAntigo, nomeNovo);
        if (afetados == 0) {
            throw new RuntimeException("Nenhum registro encontrado com o nome: " + nomeAntigo);
        }
        return afetados;
    }

    // ── DELETE (todos os registros com o nome) ───────────────
    @Transactional
    public int deletar(String nome) {
        int afetados = nomeRepository.deleteAllByNome(nome);
        if (afetados == 0) {
            throw new RuntimeException("Nenhum registro encontrado com o nome: " + nome);
        }
        return afetados;
    }

    // ── LIST ALL (util para consulta) ────────────────────────
    public List<Nome> listarTodos() {
        return nomeRepository.findAll();
    }
}
