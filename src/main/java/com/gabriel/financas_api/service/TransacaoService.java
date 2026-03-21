package com.gabriel.financas_api.service;

import com.gabriel.financas_api.dto.TransacaoDTO;
import com.gabriel.financas_api.model.Transacao;
import com.gabriel.financas_api.model.TipoTransacao;
import com.gabriel.financas_api.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public List<TransacaoDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TransacaoDTO buscarPorId(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id: " + id));
        return toDTO(transacao);
    }

    public TransacaoDTO criar(TransacaoDTO dto) {
        Transacao transacao = toEntity(dto);
        return toDTO(repository.save(transacao));
    }

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

    public List<TransacaoDTO> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByDataBetween(inicio, fim)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<TransacaoDTO> listarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria)
                .stream()
                .map(this::toDTO)
                .toList();
    }

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