package br.com.pontoonline.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pontoonline.domain.model.Salario;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Long> {

}