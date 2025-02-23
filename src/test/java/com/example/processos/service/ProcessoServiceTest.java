package com.example.processos.service;

import com.example.processos.exception.ProcessoAlreadyExistsException;
import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.repository.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProcessoServiceTest {

    @Mock
    private ProcessoRepository processoRepository;

    @InjectMocks
    private ProcessoService processoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProcesso() {
        Processo processo = new Processo(null, null, null);
        processo.setNumeroProcesso("12345");

        when(processoRepository.existsByNumeroProcesso("12345")).thenReturn(false);
        when(processoRepository.save(any(Processo.class))).thenReturn(processo);

        Processo savedProcesso = processoService.saveProcesso(processo);
        assertNotNull(savedProcesso);
        assertEquals("12345", savedProcesso.getNumeroProcesso());
    }

    @Test
    void testSaveProcessoAlreadyExists() {
        Processo processo = new Processo(null, null, null);
        processo.setNumeroProcesso("12345");

        when(processoRepository.existsByNumeroProcesso("12345")).thenReturn(true);

        assertThrows(ProcessoAlreadyExistsException.class, () -> {
            processoService.saveProcesso(processo);
        });
    }

    @Test
    void testGetAllProcessos() {
        Processo processo1 = new Processo(null, null, null);
        processo1.setNumeroProcesso("12345");

        Processo processo2 = new Processo(null, null, null);
        processo2.setNumeroProcesso("67890");

        when(processoRepository.findAll()).thenReturn(Arrays.asList(processo1, processo2));

        List<Processo> processos = processoService.getAllProcessos();
        assertEquals(2, processos.size());
    }

    @Test
    void testGetProcessoById() {
        Processo processo = new Processo(null, null, null);
        processo.setId(1L);
        processo.setNumeroProcesso("12345");

        when(processoRepository.findById(1L)).thenReturn(Optional.of(processo));

        Processo foundProcesso = processoService.getProcessoById(1L);
        assertNotNull(foundProcesso);
        assertEquals("12345", foundProcesso.getNumeroProcesso());
    }

    @Test
    void testGetProcessoByIdNotFound() {
        when(processoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProcessoNotFoundException.class, () -> {
            processoService.getProcessoById(1L);
        });
    }

    @Test
    void testDeleteProcesso() {
        when(processoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(processoRepository).deleteById(1L);

        processoService.deleteProcesso(1L);
        verify(processoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProcessoNotFound() {
        when(processoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProcessoNotFoundException.class, () -> {
            processoService.deleteProcesso(1L);
        });
    }
}