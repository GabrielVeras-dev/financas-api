package com.gabriel.financas_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/* @RestControllerAdvice intercepta todas as exceções lançadas pelos controllers
e retorna respostas padronizadas em vez de stack traces para o cliente. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata exceções de recurso não encontrado (ex: buscar transação com ID inexistente)
    // Retorna status 404 com mensagem.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("status", 404);
        erro.put("erro", "Recurso não encontrado");
        erro.put("mensagem", ex.getMessage());
        erro.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Trata erros de validação do @Valid (ex: campo obrigatório vazio, valor negativo)
    // Retorna status 400 com a lista de campos inválidos e suas mensagens.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> erro = new HashMap<>();
        Map<String, String> campos = new HashMap<>();

        // Percorre cada erro de campo e coleta o nome do campo e a mensagem de validação
        ex.getBindingResult().getFieldErrors()
                .forEach(field -> campos.put(field.getField(), field.getDefaultMessage()));

        erro.put("status", 400);
        erro.put("erro", "Dados inválidos");
        erro.put("campos", campos);
        erro.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Trata erro de body ausente ou malformado na requisição
    // Retorna status 400 em vez de 404
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("status", 400);
        erro.put("erro", "Requisição inválida");
        erro.put("mensagem", "O corpo da requisição está ausente ou com formato inválido");
        erro.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}