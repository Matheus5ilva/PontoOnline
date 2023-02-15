package br.com.pontoonline.domain.model.dto;

import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {

	private String nome;
	private TipoPessoa tipo;
	private Boolean ativo;
	private String email;

	public Empresa transformaObjeto() {
		return new Empresa(null, this.getNome(), this.getTipo(), this.getAtivo(), this.getEmail());
	}

}