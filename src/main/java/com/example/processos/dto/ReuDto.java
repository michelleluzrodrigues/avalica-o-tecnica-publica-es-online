package com.example.processos.dto;

import com.example.processos.model.Reu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReuDto {

	private Long id;
	
    private String cpf;

    private String nome;

    public static Reu toDomain(ReuDto dto) {
        return Reu.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .build();
    }
    
    public static ReuDto toDto(Reu reu) {
    	return ReuDto.builder()
    			.id(reu.getId())
    			.nome(reu.getNome())
    			.cpf(reu.getCpf())
    			.build();
    }

	
}
