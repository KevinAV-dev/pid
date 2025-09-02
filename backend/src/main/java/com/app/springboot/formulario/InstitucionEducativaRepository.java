package com.app.springboot.formulario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitucionEducativaRepository extends JpaRepository<InstitucionEducativa, Long> {

}
