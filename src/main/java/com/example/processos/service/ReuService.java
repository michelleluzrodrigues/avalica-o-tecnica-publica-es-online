package com.example.processos.service;

import com.example.processos.exception.ReuAlreadyExistsException;
import com.example.processos.exception.ReuNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.model.Reu;
import com.example.processos.repository.ReuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReuService {

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoService processoService;

    public Reu saveReu(Long processoId, Reu reu) {
        // Verifica se o processo existe
        processoService.getProcessoById(processoId);

        // Verifica se já existe um réu com o mesmo nome no mesmo processo
        if (reuRepository.existsByNomeAndProcessoId(reu.getNome(), processoId)) {
            throw new ReuAlreadyExistsException(reu.getNome(), processoId);
        }

        reu.setProcesso(new Processo(processoId, null, null)); // Associa o réu ao processo
        return reuRepository.save(reu);
    }

    public List<Reu> getReusByProcessoId(Long processoId) {
        return reuRepository.findByProcessoId(processoId);
    }

    public void deleteReu(Long id) {
        if (!reuRepository.existsById(id)) {
            throw new ReuNotFoundException(id);
        }
        reuRepository.deleteById(id);
    }
}