package com.example.processos.repository;

import com.example.processos.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    boolean existsByNumeroProcesso(String numeroProcesso);
    Processo findByNumeroProcesso(String numeroProcesso);
}