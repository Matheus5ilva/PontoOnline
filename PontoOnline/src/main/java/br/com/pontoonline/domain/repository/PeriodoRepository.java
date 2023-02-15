package br.com.pontoonline.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pontoonline.domain.model.Periodo;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

}