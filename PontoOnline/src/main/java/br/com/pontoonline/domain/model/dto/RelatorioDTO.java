package br.com.pontoonline.domain.model.dto;

import java.time.LocalDateTime;

import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.Funcionario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioDTO {

  private Empresa empresa;
  private Funcionario funcionario;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFim;
}