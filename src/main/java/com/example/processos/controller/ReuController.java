package com.example.processos.controller;

import com.example.processos.dto.ReuDto;
import com.example.processos.exception.ReuAlreadyExistsException;
import com.example.processos.exception.ReuNotFoundException;
import com.example.processos.model.Reu;
import com.example.processos.service.GetReuService;
import com.example.processos.service.SaveReuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processos/{processoId}/reus")
public class ReuController {

    @Autowired
    private SaveReuService saveReuService;
    @Autowired
    private GetReuService getReuService;

    @PostMapping
    public ResponseEntity<ReuDto> addReuToProcesso(@PathVariable Long processoId, @RequestBody Reu reu) {
        return ResponseEntity.ok(ReuDto.toDto(saveReuService.saveReu(processoId, reu)));
    }

    @GetMapping
    public ResponseEntity<List<ReuDto>> getReusByProcessoId(@PathVariable Long processoId) {
        return ResponseEntity.ok(getReuService.getReusByProcessoId(processoId).stream().map(ReuDto::toDto).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReu(@PathVariable Long id) {
        saveReuService.deleteReu(id);
        return ResponseEntity.ok().build();
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