package br.com.pontoonline.domain.model.dto;

import java.math.BigDecimal;

import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.Periodo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoDTO {

	private BigDecimal horas;
	private BigDecimal diasSemanais;
	private Empresa empresa;
	
	public Periodo transformaObjeto() {
		return new Periodo(null, this.getHoras(), this.getDiasSemanais(), this.getEmpresa());
	}
}
