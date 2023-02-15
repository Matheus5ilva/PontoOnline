package br.com.pontoonline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PontoOnlineApplication {

  private static Logger logger = LoggerFactory.getLogger(PontoOnlineApplication.class);
  
  public static void main(String[] args) {
    try{
      logger.info("Iniciando a API do Ponto Online");
    
      SpringApplication.run(PontoOnlineApplication.class, args);

      logger.info("API do Ponto Online iniciado com sucesso");
    }catch (Exception ex) {
      logger.error("Erro na inicialzação da aplicação: " + ex.getMessage());
    }  
  }
  
  @GetMapping("/")
  public String hello() {
    return "Bem - vindo ao Projeto Cocorino";
  }

}
            
