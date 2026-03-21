package com.gabriel.financas_api.controller;

import com.gabriel.financas_api.dto.ResumoFinanceiroDTO;
import com.gabriel.financas_api.dto.TransacaoDTO;
import com.gabriel.financas_api.model.TipoTransacao;
import com.gabriel.financas_api.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Camada de controle: recebe as requisições HTTP, chama o Service e devolve a resposta.
// Todos os endpoints estão mapeados sob o prefixo /api/transacoes.
@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    // GET /api/transacoes
    // Retorna a lista completa de transações cadastradas.
    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // GET /api/transacoes/{id}
    // Retorna uma transação específica pelo ID.
    // Exemplo: GET /api/transacoes/1
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /* POST /api/transacoes
    Cria uma nova transação. Retorna status 201 (Created) com o objeto criado.
    O @Valid ativa as validações definidas no DTO (campos obrigatórios, valor positivo, etc). */
    @PostMapping
    public ResponseEntity<TransacaoDTO> criar(@RequestBody @Valid TransacaoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    /* PUT /api/transacoes/{id}
    Atualiza todos os campos de uma transação existente.
    Exemplo: PUT /api/transacoes/1 */
    @PutMapping("/{id}")
    public ResponseEntity<TransacaoDTO> atualizar(@PathVariable Long id,
                                                  @RequestBody @Valid TransacaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    /* DELETE /api/transacoes/{id}
    Remove uma transação pelo ID. Retorna status 204 (No Content) sem corpo na resposta.
    Exemplo: DELETE /api/transacoes/1 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /* GET /api/transacoes/tipo/{tipo}
    Filtra as transações pelo tipo: RECEITA ou DESPESA.
    Exemplo: GET /api/transacoes/tipo/DESPESA */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<TransacaoDTO>> listarPorTipo(@PathVariable TipoTransacao tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    /* GET /api/transacoes/periodo?inicio=&fim=
    Filtra as transações dentro de um intervalo de datas.
    Exemplo: GET /api/transacoes/periodo?inicio=2026-03-01&fim=2026-03-31 (filtrou o mês de março inteiro) */
    @GetMapping("/periodo")
    public ResponseEntity<List<TransacaoDTO>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(service.listarPorPeriodo(inicio, fim));
    }

    /* GET /api/transacoes/categoria/{categoria}
    Filtra as transações por categoria.
    Exemplo: GET /api/transacoes/categoria/Alimentacao */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<TransacaoDTO>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.listarPorCategoria(categoria));
    }

    /* GET /api/transacoes/resumo
    Retorna o resumo financeiro com total de receitas, despesas e saldo.
    Exemplo de resposta: { "totalReceitas": 5000.00, "totalDespesas": 1500.00, "saldo": 3500.00 } */
    @GetMapping("/resumo")
    public ResponseEntity<ResumoFinanceiroDTO> obterResumo() {
        return ResponseEntity.ok(service.obterResumo());
    }
}