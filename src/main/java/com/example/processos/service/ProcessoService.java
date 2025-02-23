package com.example.processos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.processos.exception.ProcessoAlreadyExistsException;
import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.repository.ProcessoRepository;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    public Processo saveProcesso(Processo processo) {
        if (processoRepository.existsByNumeroProcesso(processo.getNumeroProcesso())) {
            throw new ProcessoAlreadyExistsException(processo.getNumeroProcesso());
        }
        return processoRepository.save(processo);
    }

    public List<Processo> getAllProcessos() {
        return processoRepository.findAll();
    }

    public Processo getProcessoById(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new ProcessoNotFoundException(id));
    }

    public void deleteProcesso(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new ProcessoNotFoundException(id);
        }
        processoRepository.deleteById(id);
    }
}