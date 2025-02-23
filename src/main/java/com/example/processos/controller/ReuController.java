package com.example.processos.controller;

import com.example.processos.exception.ReuAlreadyExistsException;
import com.example.processos.exception.ReuNotFoundException;
import com.example.processos.model.Reu;
import com.example.processos.service.ReuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processos/{processoId}/reus")
public class ReuController {

    @Autowired
    private ReuService reuService;

    @PostMapping
    public ResponseEntity<Reu> addReuToProcesso(@PathVariable Long processoId, @RequestBody Reu reu) {
        return ResponseEntity.ok(reuService.saveReu(processoId, reu));
    }

    @GetMapping
    public ResponseEntity<List<Reu>> getReusByProcessoId(@PathVariable Long processoId) {
        return ResponseEntity.ok(reuService.getReusByProcessoId(processoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReu(@PathVariable Long id) {
        reuService.deleteReu(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ReuAlreadyExistsException.class)
    public ResponseEntity<String> handleReuAlreadyExistsException(ReuAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ReuNotFoundException.class)
    public ResponseEntity<String> handleReuNotFoundException(ReuNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}