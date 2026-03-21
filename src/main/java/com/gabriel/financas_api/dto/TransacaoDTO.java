package com.gabriel.financas_api.dto;

import com.gabriel.financas_api.model.TipoTransacao;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/* DTO (Data Transfer Object): define o formato dos dados que a API recebe e retorna.
Separa a entidade do banco de dados da camada de comunicação da API,
evitando expor dados sensíveis ou desnecessários nas respostas. */
public class TransacaoDTO {

    private Long id;

    /*Validações automáticas do Spring Validation.
    Se alguma regra for violada, a API retorna 400 (Bad Request) com a mensagem definida. */
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Tipo é obrigatório")
    private TipoTransacao tipo;

    private String categoria; // Categoria é opcional

    public TransacaoDTO() {}

    /* Nota: originalmente seria utilizado o Lombok (@Getter, @Setter) para gerar
    os getters e setters automaticamente, eliminando este código repetitivo.
    Porém, tive uma incompatibilidade entre o Lombok e o Spring Boot 4.0.3
    que impediu o annotation processing de funcionar corretamente.
    Por isso, os métodos foram escritos manualmente. */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public TipoTransacao getTipo() { return tipo; }
    public void setTipo(TipoTransacao tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}