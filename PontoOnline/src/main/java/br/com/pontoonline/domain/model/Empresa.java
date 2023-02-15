package br.com.pontoonline.domain.model;

import javax.persistence.Entity;

import br.com.pontoonline.domain.model.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

/*
 * Classe entidade para criar Empresa
 * extendida pela classe Pessoa (Já que empresa é uma     
 * pessoa juridica(CNPJ)). A Empresa futuramente pode ter uma lista de funcionarios.
 * Criado por Matheus Alexandre - 27/10/2022
*/

@Getter
@Setter
@Entity
public class Empresa extends Pessoa {

	private static final long serialVersionUID = 1L;

	public Empresa() {
		super();
	}

	public Empresa(Long id, String nome, TipoPessoa tipo, Boolean ativo, String email) {
		super(id, nome, tipo, ativo, email);
	}

}