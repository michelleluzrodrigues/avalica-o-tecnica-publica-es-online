package com.example.processos.exception;

public class ReuAlreadyExistsException extends RuntimeException {
    public ReuAlreadyExistsException(String nome, Long processoId) {
        super("Já existe um réu com o nome '" + nome + "' no processo de ID: " + processoId);
    }
}