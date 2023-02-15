package br.com.pontoonline.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*
  * Classe entidade para criar Salario, nesta classe é responsavel por obter o valor 
  * do salario. Vale lembrar que tenho uma tabela de salario de uma empresa.
  * Foi adotado a persistencia SINGLE-TABLE(Já o padrão do JPA) 
  * O real motivo para salario estiver separado de funcionario, por modificações 
  * futuras de escalabilidade e regras de negocios.
  * Criado por Matheus Alexandre - 27/10/2022
*/

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Salario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private BigDecimal salario;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	public Salario() {
		super();
	}

	public Salario(Long id, BigDecimal salario, Empresa empresa) {
		super();
		this.id = id;
		this.salario = salario;
		this.empresa = empresa;
	}

}