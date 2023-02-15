package br.com.pontoonline.domain.model.dto;

import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.Funcionario;
import br.com.pontoonline.domain.model.Periodo;
import br.com.pontoonline.domain.model.Salario;
import br.com.pontoonline.domain.model.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDTO {

	private String nome;
	private TipoPessoa tipo;
	private Boolean ativo;
	private String email;
	private Empresa empresa;
	private Salario salario;
	private Periodo periodo;

	public Funcionario transformaObjeto() {
		return new Funcionario(null, this.getNome(), this.getTipo(), this.getAtivo(), this.getEmail(),
				this.getEmpresa(), this.getSalario(), this.getPeriodo());
	}

}