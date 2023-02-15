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
  * Classe entidade para criar Periodo, nesta classe é responsavel por obter a 
  * jornada de trabalho do funcionario. Vale lembrar que tenho uma tabela de jornada   * de trabalho de uma empresa.
  * Foi adotado a persistencia SINGLE-TABLE(Já o padrão do JPA) 
  * O real motivo para periodo estiver separado de funcionario, por modificações 
  * futuras de escalabilidade e regras de negocios.
  * Criado por Matheus Alexandre - 27/10/2022
*/

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Periodo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private BigDecimal horas;

	@Column(nullable = false)
	private BigDecimal diasSemanais;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	public Periodo(Long id, BigDecimal horas, BigDecimal diasSemanais, Empresa empresa) {
		this.id = id;
		this.horas = horas;
		this.diasSemanais = diasSemanais;
		this.empresa = empresa;
	}

	public Periodo() {
	}

}
