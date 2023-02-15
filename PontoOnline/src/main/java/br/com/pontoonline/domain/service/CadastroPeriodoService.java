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
import br.com.pontoonline.domain.model.Periodo;
import br.com.pontoonline.domain.model.dto.PeriodoDTO;
import br.com.pontoonline.domain.repository.PeriodoRepository;

@Service
public class CadastroPeriodoService {

  private static final String MSG_PERIODO_EM_USO = "Periodo de codigo %d não pode ser removida, pois esta em uso";

  private static Logger logger = LoggerFactory.getLogger(CadastroPeriodoService.class);
  
  @Autowired
  private PeriodoRepository periodoRepository;

  @Autowired
  private CadastroEmpresaService cadastroEmpresa;

  @Transactional
  public Periodo salvar(PeriodoDTO periodoDto) {

      Long empresaId = periodoDto.getEmpresa().getId();

      Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
      if (empresa.getAtivo()) {
        periodoDto.setEmpresa(empresa);
      } else {
        throw new NegocioException("Empresa está desativado");
      }

      Periodo periodo = periodoDto.transformaObjeto();

      return periodoRepository.save(periodo);
    
  }

  @Transactional
  public Periodo atualizar(Periodo periodo) {

      Long empresaId = periodo.getEmpresa().getId();

      Empresa empresa = cadastroEmpresa.buscarOuFalhar(empresaId);
      if (empresa.getAtivo()) {
        periodo.setEmpresa(empresa);
      } else {
        throw new NegocioException("Empresa está desativado");
      }

      return periodoRepository.save(periodo);
   
    
  }

  @Transactional
  public void excluir(Long periodoId) {
    try {
      periodoRepository.deleteById(periodoId);
      periodoRepository.flush();

    } catch (EmptyResultDataAccessException e) {
       logger.error("Erro ao excluir Periodo: ", e);
      throw new EntidadeNaoEncontradaException(
          String.format("Não existe um cadastro de periodo com codigo %d", periodoId));
    } catch (DataIntegrityViolationException e) {
       logger.error("Erro ao excluir Periodo: ", e);
      throw new EntidadeEmUsoException(
          String.format(MSG_PERIODO_EM_USO, periodoId));
    }
  }

  public Periodo buscarOuFalhar(Long periodoId) {
    return periodoRepository.findById(periodoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
        String.format("Não existe um cadastro de periodo com codigo %d", periodoId)));
  }
}