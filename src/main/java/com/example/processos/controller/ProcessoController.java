package com.example.processos.controller;

import java.util.List;

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

import com.example.processos.dto.ProcessoDto;
import com.example.processos.exception.ProcessoAlreadyExistsException;
import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.service.GetProcessoService;
import com.example.processos.service.SaveProcessoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/processos")
@RequiredArgsConstructor
public class ProcessoController {

    private final SaveProcessoService saveProcessoService;
    private final GetProcessoService getProcessoService;

    @PostMapping
    public ResponseEntity<ProcessoDto> saveProcesso(@RequestBody ProcessoDto processoDto) {
        return ResponseEntity.ok(ProcessoDto.toDto(saveProcessoService.saveProcesso(ProcessoDto.toDomain(processoDto))));
    }

    @GetMapping
    public ResponseEntity<List<ProcessoDto>> getAllProcessos() {
        return ResponseEntity.ok(getProcessoService.getAllProcessos().stream().map(ProcessoDto::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessoDto> getProcessoById(@PathVariable Long id) {
        return ResponseEntity.ok(ProcessoDto.toDto(getProcessoService.getProcessoById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        saveProcessoService.deleteProcesso(id);
        return ResponseEntity.ok(null);
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