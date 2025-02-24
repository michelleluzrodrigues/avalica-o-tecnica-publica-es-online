package com.example.processos.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroProcesso;

    @OneToMany(mappedBy = "processo", orphanRemoval = true)
    private List<Reu> reus = new ArrayList<>();


    public void addReu(Reu reu) {
        reus.add(reu);
        reu.setProcesso(this);
    }

    public void removeReu(Reu reu) {
        reus.remove(reu);
        reu.setProcesso(null);
    }
}