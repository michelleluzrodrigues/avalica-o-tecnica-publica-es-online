package com.example.processos.service;

import com.example.processos.exception.ReuAlreadyExistsException;
import com.example.processos.exception.ReuNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.model.Reu;
import com.example.processos.repository.ReuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveReuService {

    private final ReuRepository reuRepository;

    private final GetProcessoService getProcessoService;

    public Reu saveReu(Long processoId, Reu reu) {
        // Verifica se o processo existe
        getProcessoService.getProcessoById(processoId);

        // Verifica se já existe um réu com o mesmo cpf no mesmo processo
        if (reuRepository.existsByCpfAndProcessoId(reu.getNome(), processoId)) {
            throw new ReuAlreadyExistsException(reu.getNome(), processoId);
        }

        reu.setProcesso(new Processo(processoId, null, null)); // Associa o réu ao processo
        return reuRepository.save(reu);
    }

    public void deleteReu(Long id) {
        if (!reuRepository.existsById(id)) {
            throw new ReuNotFoundException(id);
        }
        reuRepository.deleteById(id);
    }
}