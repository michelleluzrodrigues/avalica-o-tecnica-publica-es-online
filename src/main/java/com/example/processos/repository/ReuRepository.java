package com.example.processos.repository;

import com.example.processos.model.Reu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReuRepository extends JpaRepository<Reu, Long> {
    List<Reu> findByProcessoId(Long processoId);
    boolean existsByNomeAndProcessoId(String nome, Long processoId);
}