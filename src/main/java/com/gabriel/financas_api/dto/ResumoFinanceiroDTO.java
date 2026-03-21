package com.gabriel.financas_api.dto;

import java.math.BigDecimal;

public class ResumoFinanceiroDTO {

    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldo;

    public ResumoFinanceiroDTO(BigDecimal totalReceitas, BigDecimal totalDespesas) {
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldo = totalReceitas.subtract(totalDespesas); // saldo = receitas - despesas
    }

    /* Os getters abaixo aparecem como "no usages" na IDE, mas são utilizados
    pelo Jackson em tempo de execução via reflexão para serializar o objeto em JSON.
    Sem eles, o endpoint /resumo retornaria um JSON vazio {}. */
    public BigDecimal getTotalReceitas() { return totalReceitas; }
    public BigDecimal getTotalDespesas() { return totalDespesas; }
    public BigDecimal getSaldo() { return saldo; }
}