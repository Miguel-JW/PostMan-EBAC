package com.example.repository;

import com.example.model.Nome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NomeRepository extends JpaRepository<Nome, Long> {

    // Busca todos os registros com o nome informado
    List<Nome> findByNome(String nome);

    // Atualiza TODOS os registros com nomeAntigo para nomeNovo
    @Modifying
    @Query("UPDATE Nome n SET n.nome = :nomeNovo WHERE n.nome = :nomeAntigo")
    int updateAllByNome(@Param("nomeAntigo") String nomeAntigo,
                        @Param("nomeNovo")   String nomeNovo);

    // Deleta TODOS os registros com o nome informado
    @Modifying
    @Query("DELETE FROM Nome n WHERE n.nome = :nome")
    int deleteAllByNome(@Param("nome") String nome);
}
