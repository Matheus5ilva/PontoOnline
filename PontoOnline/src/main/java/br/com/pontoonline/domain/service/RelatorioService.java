package br.com.pontoonline.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontoonline.domain.model.Ponto;
import br.com.pontoonline.domain.model.dto.RelatorioDTO;
import br.com.pontoonline.domain.model.relatorios.ValorTotal;
import br.com.pontoonline.domain.repository.PontoRepository;

@Service
public class RelatorioService {

  private static final String MSG_PONTO_EM_USO = "Ponto de codigo %d não pode ser removida, pois esta em uso";

  private static Logger logger = LoggerFactory.getLogger(CadastroPontoService.class);

  @Autowired
  private PontoRepository pontoRepository;

  @Autowired
  private CadastroEmpresaService cadastroEmpresa;

  @Autowired
  private CadastroFuncionarioService cadastroFuncionario;

  @Autowired
  private CadastroSalarioService cadastroSalario;

  @Autowired
  private CadastroPeriodoService cadastroPeriodo;

  
  @Transactional
  public ValorTotal totalGanho(RelatorioDTO relatorioDto) {

    List<Ponto> listaPonto = pontoRepository.listaPontoId(relatorioDto.getEmpresa().getId(),
        relatorioDto.getFuncionario().getId(),
        relatorioDto.getDataInicio(), relatorioDto.getDataFim());
    ValorTotal valorTotal = new ValorTotal();
    BigDecimal total = new BigDecimal(0.0);

    for(Ponto ponto : listaPonto){
     total = total.add(ponto.getValorGanho());
    }

    valorTotal.setValorTotal(total);
    return valorTotal;
  }

  
}