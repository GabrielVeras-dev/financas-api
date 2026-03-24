package com.gabriel.financas_api;

import com.gabriel.financas_api.dto.TransacaoDTO;
import com.gabriel.financas_api.model.Transacao;
import com.gabriel.financas_api.model.TipoTransacao;
import com.gabriel.financas_api.repository.TransacaoRepository;
import com.gabriel.financas_api.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith habilita o Mockito no JUnit 5
@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    // @Mock cria um objeto falso do repository — não acessa o banco de dados
    @Mock
    private TransacaoRepository repository;

    // @InjectMocks cria o Service e injeta o mock do repository automaticamente
    @InjectMocks
    private TransacaoService service;

    private Transacao transacao;
    private TransacaoDTO dto;

    // @BeforeEach roda antes de cada teste para preparar os dados
    @BeforeEach
    void setUp() {
        transacao = new Transacao();
        transacao.setId(1L);
        transacao.setDescricao("Salario");
        transacao.setValor(new BigDecimal("5000.00"));
        transacao.setData(LocalDate.of(2026, 3, 21));
        transacao.setTipo(TipoTransacao.RECEITA);
        transacao.setCategoria("Trabalho");

        dto = new TransacaoDTO();
        dto.setDescricao("Salario");
        dto.setValor(new BigDecimal("5000.00"));
        dto.setData(LocalDate.of(2026, 3, 1));
        dto.setTipo(TipoTransacao.RECEITA);
        dto.setCategoria("Trabalho");
    }

    @Test
    void deveCriarTransacaoComSucesso() {
        // Simula o comportamento do repository ao salvar
        when(repository.save(any(Transacao.class))).thenReturn(transacao);

        TransacaoDTO resultado = service.criar(dto);

        // Verifica se os dados retornados estão corretos
        assertNotNull(resultado);
        assertEquals("Salario", resultado.getDescricao());
        assertEquals(new BigDecimal("5000.00"), resultado.getValor());
        assertEquals(TipoTransacao.RECEITA, resultado.getTipo());

        // Verifica se o repository.save() foi chamado exatamente 1 vez
        verify(repository, times(1)).save(any(Transacao.class));
    }

    @Test
    void deveListarTodasTransacoes() {
        when(repository.findAll()).thenReturn(List.of(transacao));

        List<TransacaoDTO> resultado = service.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Salario", resultado.get(0).getDescricao());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarTransacaoPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(transacao));

        TransacaoDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Salario", resultado.getDescricao());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada com a mensagem correta
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.buscarPorId(99L));

        assertEquals("Transação não encontrada com id: 99", exception.getMessage());
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void deveDeletarTransacaoComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(transacao));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deletar(1L));

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveCalcularResumoFinanceiroCorretamente() {
        Transacao despesa = new Transacao();
        despesa.setTipo(TipoTransacao.DESPESA);
        despesa.setValor(new BigDecimal("1500.00"));

        when(repository.findByTipo(TipoTransacao.RECEITA)).thenReturn(List.of(transacao));
        when(repository.findByTipo(TipoTransacao.DESPESA)).thenReturn(List.of(despesa));

        var resumo = service.obterResumo();

        assertEquals(new BigDecimal("5000.00"), resumo.getTotalReceitas());
        assertEquals(new BigDecimal("1500.00"), resumo.getTotalDespesas());
        assertEquals(new BigDecimal("3500.00"), resumo.getSaldo());
    }
}