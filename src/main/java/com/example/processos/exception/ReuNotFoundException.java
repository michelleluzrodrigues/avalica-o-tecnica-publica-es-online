package com.example.processos.exception;

public class ReuNotFoundException extends RuntimeException {
    public ReuNotFoundException(Long id) {
        super("Réu não encontrado com o ID: " + id);
    }
}