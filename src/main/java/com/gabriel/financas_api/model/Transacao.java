package com.gabriel.financas_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

// Entidade JPA: representa a tabela "transacoes" no banco de dados.
// Cada instância desta classe corresponde a uma linha na tabela.

@Entity
@Table(name = "transacoes")
public class Transacao {

    // Chave primária gerada automaticamente pelo banco (auto increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    /* BigDecimal usei por ter valores monetários que garante maior precisão,
    evitando possíveis erros de arredondamento que ocorrem com double e float. */
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @Column
    private String categoria;

    public Transacao() {}

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