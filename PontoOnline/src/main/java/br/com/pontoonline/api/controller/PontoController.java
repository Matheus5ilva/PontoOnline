package br.com.pontoonline.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontoonline.domain.model.Ponto;
import br.com.pontoonline.domain.model.dto.PontoDTO;
import br.com.pontoonline.domain.repository.PontoRepository;
import br.com.pontoonline.domain.service.CadastroPontoService;

@RestController
@RequestMapping(value = "/empresa/{empresaId}/{funcionarioId}/ponto")
public class PontoController {

	@Autowired
	private PontoRepository pontoRepository;

	@Autowired
	private CadastroPontoService cadastroPonto;

	@GetMapping
	public List<Ponto> listar() {
		return pontoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Ponto salvar(@RequestBody PontoDTO pontoDto) {
		return cadastroPonto.salvar(pontoDto);
	}

}
