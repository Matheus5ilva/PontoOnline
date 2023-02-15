package br.com.pontoonline.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontoonline.domain.model.Ponto;
import br.com.pontoonline.domain.model.dto.RelatorioDTO;
import br.com.pontoonline.domain.model.relatorios.ValorTotal;
import br.com.pontoonline.domain.repository.PontoRepository;
import br.com.pontoonline.domain.service.RelatorioService;


@RestController
@RequestMapping(value = "/empresa/{empresaId}/relatorios")
public class RelatorioController {

  @Autowired
  private PontoRepository pontoRepository;

  @Autowired
  private RelatorioService relatorioService;

  @PostMapping("/{funcionarioId}/lista-pontos")
  public List<Ponto> listar(@RequestBody RelatorioDTO relatorioDto) {
    return pontoRepository.listaPontoId(relatorioDto.getEmpresa().getId(), relatorioDto.getFuncionario().getId(),
        relatorioDto.getDataInicio(), relatorioDto.getDataFim());
  }
  
 @PostMapping("/{funcionarioId}/total-ganho")
  public ValorTotal valorGanho(@RequestBody RelatorioDTO relatorioDto) {
    return relatorioService.totalGanho(relatorioDto);
  }
}
