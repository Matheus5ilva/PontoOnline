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
import br.com.pontoonline.domain.model.Salario;
import br.com.pontoonline.domain.model.dto.SalarioDTO;
import br.com.pontoonline.domain.repository.SalarioRepository;

@Service
public class CadastroSalarioService {

  private static final String MSG_SALARIO_EM_USO = "Salario de codigo %d não pode ser removida, pois esta em uso";

  private static Logger logger = LoggerFactory.getLogger(CadastroSalarioService.class);

  @Autowired
  private SalarioRepository salarioRepository;

  @Autowired
  private CadastroEmpresaService cadastroEmpresa;

  @Transactional
  public Salario salvar(SalarioDTO salarioDto) {

    Long empresaId = salarioDto.getEmpresa().getId();

    Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
    if (empresa.getAtivo()) {
      salarioDto.setEmpresa(empresa);
    } else {
      throw new NegocioException("Empresa está desativado");
    }
    Salario salario = salarioDto.transformaObjeto();

    return salarioRepository.save(salario);
  }

  @Transactional
  public Salario atualizar(Salario salario) {

    Long empresaId = salario.getEmpresa().getId();

    Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
    if (empresa.getAtivo()) {
      salario.setEmpresa(empresa);
    } else {
      throw new NegocioException("Empresa está desativado");
    }
    return salarioRepository.save(salario);
  }

  @Transactional
  public void excluir(Long salarioId) {
    try {
      salarioRepository.deleteById(salarioId);
      salarioRepository.flush();

    } catch (EmptyResultDataAccessException e) {
      throw new EntidadeNaoEncontradaException(
          String.format("Não existe um cadastro de salario com codigo %d", salarioId));
    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(
          String.format(MSG_SALARIO_EM_USO, salarioId));
    }
  }

  public Salario buscarOuFalhar(Long salarioId) {
    return salarioRepository.findById(salarioId).orElseThrow(() -> new EntidadeNaoEncontradaException(
        String.format("Não existe um cadastro de salario com codigo %d", salarioId)));
  }
}