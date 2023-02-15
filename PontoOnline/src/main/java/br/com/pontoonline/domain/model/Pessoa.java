package br.com.pontoonline.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import br.com.pontoonline.domain.model.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
  * Classe entidade para criar Empresa, Funcionario
  * Foi adotado a persistencia JOINED, pelo motivo redundancia e caso for migrar para 
  * postgresSQL, não verificar as tabelas filhas 
  * Criado por Matheus Alexandre - 27/10/2022
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private TipoPessoa tipo;

	@Column(nullable = false)
	private Boolean ativo;

	@Column(nullable = false)
	private String email;

}