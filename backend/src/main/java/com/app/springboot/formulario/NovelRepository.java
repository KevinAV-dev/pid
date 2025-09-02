package com.app.springboot.formulario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {

    @Query("SELECT n FROM Novel n WHERE n.persona.personaTipoDocumentoValor = :personaTipoDocumentoValor")
    Optional<Novel> findByPersonaTipoDocumentoValor(@Param("personaTipoDocumentoValor") String personaTipoDocumentoValor);
}
