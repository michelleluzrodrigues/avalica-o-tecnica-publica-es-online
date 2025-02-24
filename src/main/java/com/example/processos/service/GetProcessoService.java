package com.example.processos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.repository.ProcessoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProcessoService {

    private final ProcessoRepository processoRepository;

    public List<Processo> getAllProcessos() {
        return processoRepository.findAll();
    }

    public Processo getProcessoById(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new ProcessoNotFoundException(id));
    }
}