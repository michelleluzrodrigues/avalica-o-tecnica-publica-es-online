package com.example.processos.controller;

import com.example.processos.exception.ProcessoAlreadyExistsException;
import com.example.processos.exception.ProcessoNotFoundException;
import com.example.processos.model.Processo;
import com.example.processos.service.GetProcessoService;
import com.example.processos.service.SaveProcessoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProcessoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SaveProcessoService saveProcessoService;
    @Mock
    private GetProcessoService getProcessoService;

    @InjectMocks
    private ProcessoController processoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(processoController).build();
    }

    @Test
    void testSaveProcesso() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");

        when(saveProcessoService.saveProcesso(any(Processo.class))).thenReturn(processo);

        mockMvc.perform(post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(processo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroProcesso").value("12345"));
    }

    @Test
    void testSaveProcessoAlreadyExists() throws Exception {
        Processo processo = new Processo();
        processo.setNumeroProcesso("12345");

        when(saveProcessoService.saveProcesso(any(Processo.class)))
                .thenThrow(new ProcessoAlreadyExistsException("12345"));

        mockMvc.perform(post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(processo)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Já existe um processo com o número: 12345"));
    }

    @Test
    void testGetAllProcessos() throws Exception {
        Processo processo1 = new Processo();
        processo1.setNumeroProcesso("12345");

        Processo processo2 = new Processo();
        processo2.setNumeroProcesso("67890");

        when(getProcessoService.getAllProcessos()).thenReturn(Arrays.asList(processo1, processo2));

        mockMvc.perform(get("/processos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroProcesso").value("12345"))
                .andExpect(jsonPath("$[1].numeroProcesso").value("67890"));
    }

    @Test
    void testGetProcessoById() throws Exception {
        Processo processo = new Processo();
        processo.setId(1L);
        processo.setNumeroProcesso("12345");

        when(getProcessoService.getProcessoById(1L)).thenReturn(processo);

        mockMvc.perform(get("/processos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroProcesso").value("12345"));
    }

    @Test
    void testGetProcessoByIdNotFound() throws Exception {
        when(getProcessoService.getProcessoById(1L))
                .thenThrow(new ProcessoNotFoundException(1L));

        mockMvc.perform(get("/processos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Processo não encontrado com o ID: 1"));
    }

    @Test
    void testDeleteProcesso() throws Exception {
        doNothing().when(saveProcessoService).deleteProcesso(1L);

        mockMvc.perform(delete("/processos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProcessoNotFound() throws Exception {
        doThrow(new ProcessoNotFoundException(1L)).when(saveProcessoService).deleteProcesso(1L);

        mockMvc.perform(delete("/processos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Processo não encontrado com o ID: 1"));
    }
}