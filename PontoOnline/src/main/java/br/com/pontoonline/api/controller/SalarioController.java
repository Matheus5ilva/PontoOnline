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

import br.com.pontoonline.domain.model.Salario;
import br.com.pontoonline.domain.model.dto.SalarioDTO;
import br.com.pontoonline.domain.repository.SalarioRepository;
import br.com.pontoonline.domain.service.CadastroSalarioService;

@RestController
@RequestMapping(value = "/empresa/{empresaId}/salarios")
public class SalarioController {

	@Autowired
	private SalarioRepository salarioRepository;

	@Autowired
	private CadastroSalarioService cadastroSalario;

	@GetMapping
	public List<Salario> listar() {
		return salarioRepository.findAll();
	}

	@GetMapping("/{salarioId}")
	public Salario buscar(@PathVariable Long salarioId) {
		return cadastroSalario.buscarOuFalhar(salarioId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Salario salvar(@RequestBody SalarioDTO salarioDto) {
		return cadastroSalario.salvar(salarioDto);
	}

	@PutMapping("/{salarioId}")
	public Salario atualizar(@PathVariable Long salarioId, @RequestBody SalarioDTO salarioDto) {
		Salario salarioAtual = cadastroSalario.buscarOuFalhar(salarioId);
		BeanUtils.copyProperties(salarioDto, salarioAtual, "id");
		return cadastroSalario.atualizar(salarioAtual);
	}

	@DeleteMapping("/{salarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long salarioId) {
		cadastroSalario.excluir(salarioId);
	}

}
