package br.com.pontoonline.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pontoonline.domain.model.Ponto;
import br.com.pontoonline.domain.model.enums.StatusPonto;

import java.time.LocalDateTime;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Long>, JpaSpecificationExecutor<Ponto> {

	@Query("from Ponto where empresa.id = :empresa and funcionario.id = :funcionario and status = :status")
	Ponto buscarPontoAberto(@Param("empresa") Long empresa, @Param("funcionario") Long funcionario,
			@Param("status") StatusPonto status);

  @Query("from Ponto where empresa.id = :empresa and pontoFechamento between :dataInicio and :dataFim")
  List<Ponto> listaPontoAll(@Param("empresa") Long empresa,  @Param("dataInicio")LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
  
  @Query("from Ponto where empresa.id = :empresa and funcionario.id = :funcionario and pontoFechamento between :dataInicio and :dataFim")
  List<Ponto> listaPontoId(@Param("empresa") Long empresa, @Param("funcionario") Long funcionario, @Param("dataInicio")LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
}