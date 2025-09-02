package com.app.springboot.formulario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorreoRepository extends JpaRepository<Correo, Long> {

    @Modifying
    @Query("UPDATE Correo c SET c.correoDescripcion = :nuevoCorreo WHERE c.persona.personaId = :personaId")
    int updateCorreoByPersonaId(@Param("personaId") Long personaId, @Param("nuevoCorreo") String nuevoCorreo);
}
