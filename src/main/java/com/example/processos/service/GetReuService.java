package com.example.processos.service;

import com.example.processos.model.Reu;
import com.example.processos.repository.ReuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetReuService {

    private final ReuRepository reuRepository;

    public List<Reu> getReusByProcessoId(Long processoId) {
        return reuRepository.findByProcessoId(processoId);
    }

}