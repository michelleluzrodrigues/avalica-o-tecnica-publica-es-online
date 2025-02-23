package com.example.processos.exception;

public class ProcessoAlreadyExistsException extends RuntimeException {
    public ProcessoAlreadyExistsException(String numeroProcesso) {
        super("Já existe um processo com o número: " + numeroProcesso);
    }
}