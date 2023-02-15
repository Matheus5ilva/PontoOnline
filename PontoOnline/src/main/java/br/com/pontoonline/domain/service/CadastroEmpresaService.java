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
import br.com.pontoonline.domain.model.dto.EmpresaDTO;
import br.com.pontoonline.domain.repository.EmpresaRepository;

@Service
public class CadastroEmpresaService {

  private static final String MSG_EMPRESA_EM_USO = "Empresa de codigo %d não pode ser removida, pois esta em uso";
  
  private static Logger logger = LoggerFactory.getLogger(CadastroEmpresaService.class);

  @Autowired
  private EmpresaRepository empresaRepository;

  @Transactional
  public Empresa salvar(EmpresaDTO empresaDto) {
      logger.info("Salvando a empresa");
      Empresa empresa = empresaDto.transformaObjeto();
      return empresaRepository.save(empresa);
    
  }

  @Transactional
  public Empresa atualizar(Empresa empresa) {
      logger.info("Atualizando a empresa");
      return empresaRepository.save(empresa);
    
  }

  @Transactional
  public void excluir(Long empresaId) {
    try {
      logger.info("Excluindo a empresa");
      empresaRepository.deleteById(empresaId);
      empresaRepository.flush();

    } catch (EmptyResultDataAccessException e) {
      logger.error("Erro ao excluir Empresa: ", e);
      throw new EntidadeNaoEncontradaException(
          String.format("Não existe um cadastro de empresa com codigo %d", empresaId));

    } catch (DataIntegrityViolationException e) {
      logger.error("Erro ao excluir Empresa: ", e);
      throw new EntidadeEmUsoException(
          String.format(MSG_EMPRESA_EM_USO, empresaId));
    }
  }

  public Empresa buscarOuFalhar(Long empresaId) {
    return empresaRepository.findById(empresaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
        String.format("Não existe um cadastro de empresa com codigo %d", empresaId)));
  }
}