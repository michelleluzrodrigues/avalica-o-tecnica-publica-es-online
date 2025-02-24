package com.example.processos.service;

import com.example.processos.exception.ProcessoAlreadyExistsException;
import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.repository.ProcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveProcessoService {

    private final ProcessoRepository processoRepository;

    private final SaveReuService saveReuService;

    public Processo saveProcesso(Processo processo) {
        if (processoRepository.existsByNumeroProcesso(processo.getNumeroProcesso())) {
            throw new ProcessoAlreadyExistsException(processo.getNumeroProcesso());
        }
        var processoSalvo = processoRepository.save(processo);
        processo.getReus().forEach(reu -> {
            reu.setProcesso(processoSalvo);
            saveReuService.saveReu(processo.getId(), reu);
        });

        return processoSalvo;
    }

    public void deleteProcesso(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new ProcessoNotFoundException(id);
        }
        processoRepository.deleteById(id);
    }
}