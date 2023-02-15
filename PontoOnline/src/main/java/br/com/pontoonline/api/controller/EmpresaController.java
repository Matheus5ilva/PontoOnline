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

import br.com.pontoonline.domain.model.Empresa;
import br.com.pontoonline.domain.model.dto.EmpresaDTO;
import br.com.pontoonline.domain.repository.EmpresaRepository;
import br.com.pontoonline.domain.service.CadastroEmpresaService;

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private CadastroEmpresaService cadastroEmpresa;

	@GetMapping
	public List<Empresa> listar() {
		return empresaRepository.findAll();
	}

	@GetMapping("/{empresaId}")
	public Empresa buscar(@PathVariable Long empresaId) {
		return cadastroEmpresa.buscarOuFalhar(empresaId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Empresa salvar(@RequestBody EmpresaDTO empresaDto) {
		return cadastroEmpresa.salvar(empresaDto);
	}

	@PutMapping("/{empresaId}")
	public Empresa atualizar(@PathVariable Long empresaId, @RequestBody EmpresaDTO empresaDto) {
		Empresa empresaAtual = cadastroEmpresa.buscarOuFalhar(empresaId);
		BeanUtils.copyProperties(empresaDto, empresaAtual, "id");
		return cadastroEmpresa.atualizar(empresaAtual);
	}

	@DeleteMapping("/{empresaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long empresaId) {
		cadastroEmpresa.excluir(empresaId);
	}

}
