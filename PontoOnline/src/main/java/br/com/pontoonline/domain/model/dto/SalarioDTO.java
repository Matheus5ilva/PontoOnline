package br.com.pontoonline.domain.model.dto;

import java.math.BigDecimal;

import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.Salario;
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
public class SalarioDTO {

	private BigDecimal salario;
	private Empresa empresa;
	
	public Salario transformaObjeto() {
		return new Salario(null, this.getSalario(), this.getEmpresa());
	}

}