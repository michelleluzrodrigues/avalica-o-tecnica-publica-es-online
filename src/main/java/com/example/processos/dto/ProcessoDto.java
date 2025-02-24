package com.example.processos.dto;

import com.example.processos.model.Processo;
import com.example.processos.model.Reu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessoDto {

	private Long id;

	private String numeroProcesso;

	private List<ReuDto> reus = new ArrayList<>();

	public static Processo toDomain(ProcessoDto dto) {
		return Processo.builder().id(dto.getId()).numeroProcesso(dto.getNumeroProcesso())
				.reus(dto.getReus().stream().map(ReuDto::toDomain).toList()).build();
	}

	public static ProcessoDto toDto(Processo processo) {
		return ProcessoDto.builder().numeroProcesso(processo.getNumeroProcesso()).id(processo.getId())
				.reus(processo.getReus().stream().map(ReuDto::toDto).toList()).build();
	}
}
