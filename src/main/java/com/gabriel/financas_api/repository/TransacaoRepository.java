package com.gabriel.financas_api.repository;

import com.gabriel.financas_api.model.Transacao;
import com.gabriel.financas_api.model.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/* Camada de repositório: responsável pela comunicação com o banco de dados.
Ao herdar JpaRepository, temos os métodos:
save(), findById(), findAll(), deleteById(), count(), entre outros.
O primeiro tipo (Transacao) é a entidade e o segundo (Long) é o tipo do ID. */

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Gera: SELECT * FROM transacoes WHERE tipo = ?
    // Exemplo: findByTipo(TipoTransacao.DESPESA) gera SELECT * FROM transacoes WHERE tipo = 'DESPESA'
    List<Transacao> findByTipo(TipoTransacao tipo);

    // Gera: SELECT * FROM transacoes WHERE data BETWEEN ? AND ?
    // Exemplo: findByDataBetween(2026-03-01, 2026-03-31) retorna todas as transações de março.
    List<Transacao> findByDataBetween(LocalDate inicio, LocalDate fim);

    // Gera: SELECT * FROM transacoes WHERE categoria = ?
    // Exemplo: findByCategoria("Alimentacao") retorna todas as transações dessa categoria.
    List<Transacao> findByCategoria(String categoria);
}