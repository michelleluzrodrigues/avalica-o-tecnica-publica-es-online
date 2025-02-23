package com.example.processos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.processos.exception.ProcessoAlreadyExistsException;
import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.service.ProcessoService;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<Processo> saveProcesso(@RequestBody Processo processo) {
        return ResponseEntity.ok(processoService.saveProcesso(processo));
    }

    @GetMapping
    public ResponseEntity<List<Processo>> getAllProcessos() {
        return ResponseEntity.ok(processoService.getAllProcessos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> getProcessoById(@PathVariable Long id) {
        return ResponseEntity.ok(processoService.getProcessoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        processoService.deleteProcesso(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProcessoAlreadyExistsException.class)
    public ResponseEntity<String> handleProcessoAlreadyExistsException(ProcessoAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProcessoNotFoundException.class)
    public ResponseEntity<String> handleProcessoNotFoundException(ProcessoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}