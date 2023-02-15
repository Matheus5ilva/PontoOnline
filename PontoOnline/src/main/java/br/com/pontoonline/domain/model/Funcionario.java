package br.com.pontoonline.domain.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.pontoonline.domain.model.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

/*
  * Classe entidade para criar Funcionario
  * É extendida pela classe Pessoa (Já que funcionario é uma     
  * pessoa(CPF)). O Funcionario tem que trabalhar numa empresa e possuir Salario e Periodo 
  * Criado por Matheus Alexandre - 27/10/2022
*/

@Getter
@Setter
@Entity
public class Funcionario extends Pessoa {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Salario salario;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Periodo periodo;

	public Funcionario() {
		super();
	}

	public Funcionario(Long id, String nome, TipoPessoa tipo, Boolean ativo, String email, Empresa empresa, Salario salario, Periodo periodo) {
		super(id, nome, tipo, ativo, email);
		this.empresa = empresa;
		this.salario = salario;
		this.periodo = periodo;
	}

	

	

	
	
}