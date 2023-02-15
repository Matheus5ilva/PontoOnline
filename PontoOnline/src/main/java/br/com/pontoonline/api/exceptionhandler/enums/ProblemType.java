package br.com.pontoonline.api.exceptionhandler.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrada", "Recurso n�o encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Viola��o de regra de neg�cio"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Par�metro inv�lido"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://localhost:8080" + path;
		this.title = title;
	}
}