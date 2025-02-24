package com.example.processos.service;

import com.example.processos.exception.ReuAlreadyExistsException;
import com.example.processos.exception.ReuNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.model.Reu;
import com.example.processos.repository.ReuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReuServiceTest {

    @Mock
    private ReuRepository reuRepository;

    @Mock
    private SaveProcessoService saveProcessoService;
    @Mock
    private GetProcessoService getProcessoService;

    @InjectMocks
    private SaveReuService saveReuService;
    @InjectMocks
    private GetReuService getReuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveReu() {
        Reu reu = new Reu();
        reu.setNome("João Silva");

        when(getProcessoService.getProcessoById(1L)).thenReturn(new Processo(null, null, null));
        when(reuRepository.existsByCpfAndProcessoId("João Silva", 1L)).thenReturn(false);
        when(reuRepository.save(any(Reu.class))).thenReturn(reu);

        Reu savedReu = saveReuService.saveReu(1L, reu);
        assertNotNull(savedReu);
        assertEquals("João Silva", savedReu.getNome());
    }

    @Test
    void testSaveReuAlreadyExists() {
        Reu reu = new Reu();
        reu.setNome("João Silva");

        when(getProcessoService.getProcessoById(1L)).thenReturn(new Processo(null, null, null));
        when(reuRepository.existsByCpfAndProcessoId("João Silva", 1L)).thenReturn(true);

        assertThrows(ReuAlreadyExistsException.class, () -> {
            saveReuService.saveReu(1L, reu);
        });
    }

    @Test
    void testGetReusByProcessoId() {
        Reu reu1 = new Reu();
        reu1.setNome("João Silva");

        Reu reu2 = new Reu();
        reu2.setNome("Maria Souza");

        when(reuRepository.findByProcessoId(1L)).thenReturn(Arrays.asList(reu1, reu2));

        List<Reu> reus = getReuService.getReusByProcessoId(1L);
        assertEquals(2, reus.size());
    }

    @Test
    void testDeleteReu() {
        when(reuRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reuRepository).deleteById(1L);

        saveReuService.deleteReu(1L);
        verify(reuRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReuNotFound() {
        when(reuRepository.existsById(1L)).thenReturn(false);

        assertThrows(ReuNotFoundException.class, () -> {
            saveReuService.deleteReu(1L);
        });
    }
}