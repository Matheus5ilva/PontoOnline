package br.com.pontoonline.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontoonline.domain.model.Periodo;
import br.com.pontoonline.domain.model.dto.PeriodoDTO;
import br.com.pontoonline.domain.repository.PeriodoRepository;
import br.com.pontoonline.domain.service.CadastroPeriodoService;

@RestController
@RequestMapping(value = "/empresa/{empresaId}/periodos")
public class PeriodoController {

	@Autowired
	private PeriodoRepository periodoRepository;

	@Autowired
	private CadastroPeriodoService cadastroPeriodo;

	@GetMapping
	public List<Periodo> listar() {
		return periodoRepository.findAll();
	}

	@GetMapping("/{periodoId}")
	public Periodo buscar(@PathVariable Long periodoId) {
		return cadastroPeriodo.buscarOuFalhar(periodoId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Periodo salvar(@RequestBody PeriodoDTO periodoDto) {
		return cadastroPeriodo.salvar(periodoDto);
	}

	@PutMapping("/{periodoId}")
	public Periodo atualizar(@PathVariable Long periodoId, @RequestBody PeriodoDTO periodoDto) {
		Periodo periodoAtual = cadastroPeriodo.buscarOuFalhar(periodoId);
		BeanUtils.copyProperties(periodoDto, periodoAtual, "id");
		return cadastroPeriodo.atualizar(periodoAtual);
	}

	@DeleteMapping("/{periodoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long periodoId) {
		cadastroPeriodo.excluir(periodoId);
	}

}
