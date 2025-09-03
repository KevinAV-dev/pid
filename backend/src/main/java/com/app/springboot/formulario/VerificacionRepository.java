package com.app.springboot.formulario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificacionRepository extends JpaRepository<Verificacion, Long> {

    Optional<Verificacion> findByNovel_NovelId(Long novelId);

}
