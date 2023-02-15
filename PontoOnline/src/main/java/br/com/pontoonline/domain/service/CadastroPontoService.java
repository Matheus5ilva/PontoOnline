package br.com.pontoonline.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontoonline.domain.exception.*;
import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.Funcionario;
import br.com.pontoonline.domain.model.Periodo;
import br.com.pontoonline.domain.model.Ponto;
import br.com.pontoonline.domain.model.Salario;
import br.com.pontoonline.domain.model.dto.PontoDTO;
import br.com.pontoonline.domain.model.dto.RelatorioDTO;
import br.com.pontoonline.domain.model.enums.StatusPonto;
import br.com.pontoonline.domain.repository.PontoRepository;

@Service
public class CadastroPontoService {

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
  public Ponto salvar(PontoDTO ponto) {

      Long empresaId = ponto.getEmpresa().getId();
      Long funcionarioId = ponto.getFuncionario().getId();

      Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
      if (empresa.getAtivo()) {
        ponto.setEmpresa(empresa);
      } else {
        throw new NegocioException("Empresa está desativado");
      }

      Funcionario funcionario = cadastroFuncionario.buscarOuFalhar(funcionarioId);
      if (funcionario.getAtivo()) {
        ponto.setFuncionario(funcionario);
      } else {
        throw new NegocioException("Funcionario está desativado");
      }

      Ponto pontoAberto = this.buscarPontoAberto(empresaId, funcionarioId);

      if (pontoAberto != null) {
        pontoAberto.setPontoFechamento(ponto.getPonto());
        pontoAberto.setStatus(StatusPonto.FECHADO);
        pontoAberto.setValorGanho(this.calcularValorGanho(pontoAberto));
        return pontoRepository.save(pontoAberto);
      } else {
        Ponto novoPonto = new Ponto();

        novoPonto.setEmpresa(empresa);
        novoPonto.setFuncionario(funcionario);
        novoPonto.setPontoAbertura(ponto.getPonto());
        novoPonto.setStatus(StatusPonto.ABERTO);

        return pontoRepository.save(novoPonto);
      }
  }

  @Transactional
  public void excluir(Long pontoId) {
    try {
      pontoRepository.deleteById(pontoId);
      pontoRepository.flush();

    } catch (EmptyResultDataAccessException e) {
      logger.error("Erro ao excluir Ponto: ", e);
      throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de ponto com codigo %d", pontoId));
    } catch (DataIntegrityViolationException e) {
      logger.error("Erro ao excluir Ponto: ", e);
      throw new EntidadeEmUsoException(
          String.format(MSG_PONTO_EM_USO, pontoId));
    }
  }

  public Ponto buscarOuFalhar(Long pontoId) {
    return pontoRepository.findById(pontoId).orElseThrow(
        () -> new EntidadeNaoEncontradaException(
            String.format("Não existe um cadastro de ponto com codigo %d", pontoId)));
  }

  public Ponto buscarPontoAberto(Long empresa, Long funcionario) {
    return pontoRepository.buscarPontoAberto(empresa, funcionario, StatusPonto.ABERTO);
  }

  private BigDecimal calcularValorGanho(Ponto ponto) {
    try {
      Funcionario funcionario = cadastroFuncionario.buscarOuFalhar(ponto.getFuncionario().getId());
      Salario salario = cadastroSalario.buscarOuFalhar(funcionario.getSalario().getId());
      Periodo periodo = cadastroPeriodo.buscarOuFalhar(funcionario.getPeriodo().getId());
      BigDecimal mes = new BigDecimal(4.0);

      BigDecimal horaMes = new BigDecimal(periodo.getHoras().doubleValue())
          .multiply(new BigDecimal(periodo.getDiasSemanais().doubleValue()))
          .multiply(new BigDecimal(mes.doubleValue()));

      BigDecimal salarioPorSegundo = new BigDecimal(salario.getSalario().doubleValue())
          .divide(new BigDecimal(horaMes.doubleValue()), 5, RoundingMode.DOWN)
          .divide(new BigDecimal(3600.0), 5, RoundingMode.DOWN);
      BigDecimal periodoAberto = new BigDecimal(
          Duration.between(ponto.getPontoAbertura(), ponto.getPontoFechamento()).getSeconds());

      BigDecimal valorGanho = new BigDecimal(periodoAberto.doubleValue())
          .multiply(new BigDecimal(salarioPorSegundo.doubleValue()));
      return valorGanho;
    } catch (Exception e) {
      logger.error("Erro ao salvar ponto: ", e);
    }
    return null;

  }
}