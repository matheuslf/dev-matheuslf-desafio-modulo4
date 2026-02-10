package dev.matheuslf.relogios.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class TratadorGlobalException {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroApi> tratarNaoEncontrado(NaoEncontradoException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErroApi(Instant.now(), 404, "Não encontrado", ex.getMessage(), req.getRequestURI(), List.of())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroApi> tratarRequisicaoInvalida(IllegalArgumentException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErroApi(Instant.now(), 400, "Requisição inválida", ex.getMessage(), req.getRequestURI(), List.of())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApi> tratarValidacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ErroApi.ErroCampo> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErroApi.ErroCampo(fe.getField(), mensagem(fe)))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErroApi(Instant.now(), 400, "Requisição inválida", "Erro de validação", req.getRequestURI(), campos)
        );
    }

    private String mensagem(FieldError fe) {
        return fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "inválido";
    }
}
