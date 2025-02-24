package com.example.processos.controller;

import com.example.processos.exception.ReuAlreadyExistsException;
import com.example.processos.exception.ReuNotFoundException;
import com.example.processos.model.Reu;
import com.example.processos.service.GetReuService;
import com.example.processos.service.SaveReuService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReuControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SaveReuService saveReuService;
    @Mock
    private GetReuService getReuService;

    @InjectMocks
    private ReuController reuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reuController).build();
    }

    @Test
    void testAddReuToProcesso() throws Exception {
        Reu reu = new Reu();
        reu.setNome("João Silva");

        when(saveReuService.saveReu(any(Long.class), any(Reu.class))).thenReturn(reu);

        mockMvc.perform(post("/processos/1/reus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(reu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void testAddReuToProcessoAlreadyExists() throws Exception {
        Reu reu = new Reu();
        reu.setNome("João Silva");

        when(saveReuService.saveReu(any(Long.class), any(Reu.class)))
                .thenThrow(new ReuAlreadyExistsException("João Silva", 1L));

        mockMvc.perform(post("/processos/1/reus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(reu)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Já existe um réu com o nome 'João Silva' no processo de ID: 1"));
    }

    @Test
    void testGetReusByProcessoId() throws Exception {
        Reu reu1 = new Reu();
        reu1.setNome("João Silva");

        Reu reu2 = new Reu();
        reu2.setNome("Maria Souza");

        when(getReuService.getReusByProcessoId(1L)).thenReturn(Arrays.asList(reu1, reu2));

        mockMvc.perform(get("/processos/1/reus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].nome").value("Maria Souza"));
    }

    @Test
    void testDeleteReu() throws Exception {
        doNothing().when(saveReuService).deleteReu(1L);

        mockMvc.perform(delete("/processos/1/reus/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteReuNotFound() throws Exception {
        doThrow(new ReuNotFoundException(1L)).when(saveReuService).deleteReu(1L);

        mockMvc.perform(delete("/processos/1/reus/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Réu não encontrado com o ID: 1"));
    }
}