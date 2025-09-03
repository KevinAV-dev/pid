package com.app.springboot.formulario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Long> {

    @Modifying
    @Query("UPDATE Telefono c SET c.telefonoDescripcion = :nuevoTelefono WHERE c.persona.personaId = :personaId")
    int updateTelefonoByPersonaId(@Param("personaId") Long personaId, @Param("nuevoTelefono") String nuevoTelefono);

    Optional<Telefono> findByTelefonoDescripcionAndPersona_PersonaIdNot(String telefonoDescripcion, Long persona_personaId);
}
