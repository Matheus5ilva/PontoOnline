package br.com.pontoonline.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontoonline.domain.exception.*;
import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.Funcionario;
import br.com.pontoonline.domain.model.Periodo;
import br.com.pontoonline.domain.model.Salario;
import br.com.pontoonline.domain.model.dto.FuncionarioDTO;
import br.com.pontoonline.domain.repository.FuncionarioRepository;

@Service
public class CadastroFuncionarioService {

  private static final String MSG_FUNCIONARIO_EM_USO = "Funcionario de codigo %d não pode ser removida, pois esta em uso";

  private static Logger logger = LoggerFactory.getLogger(CadastroFuncionarioService.class);

  @Autowired
  private FuncionarioRepository funcionarioRepository;

  @Autowired
  private CadastroEmpresaService cadastroEmpresa;

  @Autowired
  private CadastroSalarioService cadastroSalario;

  @Autowired
  private CadastroPeriodoService cadastroPeriodo;

  @Transactional
  public Funcionario salvar(FuncionarioDTO funcionarioDto) {

      logger.info("Salvando funcionario: ");
      Long empresaId = funcionarioDto.getEmpresa().getId();
      Long salarioId = funcionarioDto.getSalario().getId();
      Long periodoId = funcionarioDto.getPeriodo().getId();

      Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
      if (empresa.getAtivo()) {
        funcionarioDto.setEmpresa(empresa);
      } else {
        logger.error("Empresa está desativado: ");
        throw new NegocioException("Empresa está desativado");
      }

      Salario salario = cadastroSalario.buscarOuFalhar(salarioId);
      funcionarioDto.setSalario(salario);

      Periodo periodo = cadastroPeriodo.buscarOuFalhar(periodoId);
      funcionarioDto.setPeriodo(periodo);

      Funcionario funcionario = funcionarioDto.transformaObjeto();

      return funcionarioRepository.save(funcionario);
   
  }

  @Transactional
  public Funcionario atualizar(Funcionario funcionario) {

      logger.info("Atualizando funcionario");
      Long empresaId = funcionario.getEmpresa().getId();
      Long salarioId = funcionario.getSalario().getId();
      Long periodoId = funcionario.getPeriodo().getId();

      Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
      if (empresa.getAtivo()) {
        funcionario.setEmpresa(empresa);
      } else {
        logger.warn("Empresa desativado: ");
        throw new NegocioException("Empresa está desativado");
      }

      Salario salario = cadastroSalario.buscarOuFalhar(salarioId);
      funcionario.setSalario(salario);

      Periodo periodo = cadastroPeriodo.buscarOuFalhar(periodoId);
      funcionario.setPeriodo(periodo);

      return funcionarioRepository.save(funcionario);

  }

  @Transactional
  public void excluir(Long funcionarioId) {
    try {
      logger.info("Iniciando a exclusão: ");
      funcionarioRepository.deleteById(funcionarioId);
      funcionarioRepository.flush();

    } catch (EmptyResultDataAccessException e) {
      logger.error("Erro ao excluir Funcionario: ", e);
      throw new EntidadeNaoEncontradaException(
          String.format("Não existe um cadastro de funcionario com codigo %d", funcionarioId));
    } catch (DataIntegrityViolationException e) {
      logger.error("Erro ao excluir Funcionario: ", e);
      throw new EntidadeEmUsoException(
          String.format(MSG_FUNCIONARIO_EM_USO, funcionarioId));
    }
  }

  public Funcionario buscarOuFalhar(Long funcionarioId) {
    return funcionarioRepository.findById(funcionarioId).orElseThrow(() -> new EntidadeNaoEncontradaException(
        String.format("Não existe um cadastro de funcionario com codigo %d", funcionarioId)));
  }
}