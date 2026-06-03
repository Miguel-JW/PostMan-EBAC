package com.example.controller;

import com.example.model.Nome;
import com.example.service.NomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nomes")
@RequiredArgsConstructor
public class NomeController {

    private final NomeService nomeService;

    /**
     * POST /nomes
     * Body: { "nome": "João" }
     * Insere um novo nome no banco.
     */
    @PostMapping
    public ResponseEntity<Nome> inserir(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
        if (nome == null || nome.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Nome salvo = nomeService.inserir(nome);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * PUT /nomes
     * Body: { "nomeAntigo": "João", "nomeNovo": "João Silva" }
     * Modifica TODOS os registros com nomeAntigo para nomeNovo.
     */
    @PutMapping
    public ResponseEntity<Map<String, Object>> atualizar(@RequestBody Map<String, String> body) {
        String nomeAntigo = body.get("nomeAntigo");
        String nomeNovo   = body.get("nomeNovo");

        if (nomeAntigo == null || nomeNovo == null || nomeAntigo.isBlank() || nomeNovo.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Campos 'nomeAntigo' e 'nomeNovo' são obrigatórios."));
        }

        try {
            int afetados = nomeService.atualizar(nomeAntigo, nomeNovo);
            return ResponseEntity.ok(Map.of(
                    "mensagem",       "Nomes atualizados com sucesso.",
                    "registrosAlterados", afetados
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * DELETE /nomes
     * Body: { "nome": "João" }
     * Deleta TODOS os registros com o nome informado.
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deletar(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");

        if (nome == null || nome.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Campo 'nome' é obrigatório."));
        }

        try {
            int afetados = nomeService.deletar(nome);
            return ResponseEntity.ok(Map.of(
                    "mensagem",        "Nomes deletados com sucesso.",
                    "registrosDeletados", afetados
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * GET /nomes
     * Lista todos os nomes cadastrados (útil para verificação).
     */
    @GetMapping
    public ResponseEntity<List<Nome>> listar() {
        return ResponseEntity.ok(nomeService.listarTodos());
    }
}
