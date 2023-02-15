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

import br.com.pontoonline.domain.model.Funcionario;
import br.com.pontoonline.domain.model.dto.FuncionarioDTO;
import br.com.pontoonline.domain.repository.FuncionarioRepository;
import br.com.pontoonline.domain.service.CadastroFuncionarioService;

@RestController
@RequestMapping(value = "/empresa/{empresaId}/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private CadastroFuncionarioService cadastroFuncionario;

	@GetMapping
	public List<Funcionario> listar() {
		return funcionarioRepository.findAll();
	}

	@GetMapping("/{funcionarioId}")
	public Funcionario buscar(@PathVariable Long funcionarioId) {
		return cadastroFuncionario.buscarOuFalhar(funcionarioId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Funcionario salvar(@RequestBody FuncionarioDTO funcionarioDto) {
		return cadastroFuncionario.salvar(funcionarioDto);
	}

	@PutMapping("/{funcionarioId}")
	public Funcionario atualizar(@PathVariable Long funcionarioId, @RequestBody FuncionarioDTO funcionarioDto) {
		Funcionario funcionarioAtual = cadastroFuncionario.buscarOuFalhar(funcionarioId);
		BeanUtils.copyProperties(funcionarioDto, funcionarioAtual, "id");
		return cadastroFuncionario.atualizar(funcionarioAtual);
	}

	@DeleteMapping("/{funcionarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long funcionarioId) {
		cadastroFuncionario.excluir(funcionarioId);
	}

}