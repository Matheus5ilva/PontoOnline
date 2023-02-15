package br.com.pontoonline.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.pontoonline.domain.model.enums.StatusPonto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Ponto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	private LocalDateTime pontoAbertura;

	private LocalDateTime pontoFechamento;

	private BigDecimal valorGanho;

	private StatusPonto status;

}