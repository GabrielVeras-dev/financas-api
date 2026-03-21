package com.gabriel.financas_api.service;

import com.gabriel.financas_api.dto.ResumoFinanceiroDTO;
import com.gabriel.financas_api.dto.TransacaoDTO;
import com.gabriel.financas_api.model.Transacao;
import com.gabriel.financas_api.model.TipoTransacao;
import com.gabriel.financas_api.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

// Camada de serviço: contém as regras de negócio da aplicação.
// É chamada pelo Controller e chama o Repository para acessar o banco de dados.

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    // Busca todas as transações e converte cada uma de entidade para DTO.
    public List<TransacaoDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Busca uma transação pelo ID. Caso não exista, lança uma exceção com mensagem.
    public TransacaoDTO buscarPorId(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id: " + id));
        return toDTO(transacao);
    }

    // Converte o DTO recebido para entidade, salva no banco e retorna o DTO com o ID gerado.
    public TransacaoDTO criar(TransacaoDTO dto) {
        Transacao transacao = toEntity(dto);
        return toDTO(repository.save(transacao));
    }

    // Busca a transação pelo Id, atualiza os campos e salva novamente no banco.
    public TransacaoDTO atualizar(Long id, TransacaoDTO dto) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id: " + id));

        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(dto.getValor());
        transacao.setData(dto.getData());
        transacao.setTipo(dto.getTipo());
        transacao.setCategoria(dto.getCategoria());

        return toDTO(repository.save(transacao));
    }

    // Verifica se a transação existe antes de deletar para garantir uma mensagem de erro
    public void deletar(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id: " + id));
        repository.deleteById(id);
    }

    public List<TransacaoDTO> listarPorTipo(TipoTransacao tipo) {
        return repository.findByTipo(tipo)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    /* Filtra as transações dentro de um intervalo de datas (inicio até fim).
    Exemplo: GET /api/transacoes/periodo?inicio=2026-03-01&fim=2026-03-31
    Retorna todas as transações do mês de março de 2026. */
    public List<TransacaoDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByDataBetween(inicio, fim)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Filtra as transações por categoria (ex: Alimentacao, Moradia, Trabalho).
    public List<TransacaoDTO> listarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    /* Calcula o resumo financeiro somando todas as receitas e despesas separadamente.
    O reduce percorre a lista somando os valores, começando do zero (BigDecimal.ZERO).
    O saldo é calculado dentro do ResumoFinanceiroDTO: saldo = receitas - despesas. */
    public ResumoFinanceiroDTO obterResumo() {
        BigDecimal totalReceitas = repository.findByTipo(TipoTransacao.RECEITA)
                .stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = repository.findByTipo(TipoTransacao.DESPESA)
                .stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResumoFinanceiroDTO(totalReceitas, totalDespesas);
    }

    /* Converte a entidade Transacao para TransacaoDTO.
    Usado para não expor diretamente a entidade do banco na resposta da API. */
    private TransacaoDTO toDTO(Transacao transacao) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setId(transacao.getId());
        dto.setDescricao(transacao.getDescricao());
        dto.setValor(transacao.getValor());
        dto.setData(transacao.getData());
        dto.setTipo(transacao.getTipo());
        dto.setCategoria(transacao.getCategoria());
        return dto;
    }

    /* Converte um TransacaoDTO para entidade Transacao.
    O ID não é setado pois é gerado automaticamente pelo banco. */
    private Transacao toEntity(TransacaoDTO dto) {
        Transacao transacao = new Transacao();
        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(dto.getValor());
        transacao.setData(dto.getData());
        transacao.setTipo(dto.getTipo());
        transacao.setCategoria(dto.getCategoria());
        return transacao;
    }
}