package com.app.springboot.formulario;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class FormularioServicio {

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private CorreoRepository correoRepository;

    @Autowired
    private TelefonoRepository telefonoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private InstitucionEducativaRepository institucionEducativaRepository;

    public Novel getInfo(String dni) {
        return novelRepository.findByPersonaTipoDocumentoValor(dni).orElse(null);
    }

    @Transactional
    public String updateInfo(FormularioData datos){

        if (datos.getCorreoId() != null) {
            correoRepository.updateCorreoByPersonaId(datos.getNovelId(), datos.getCorreo());
        } else {
            Correo nuevoCorreo = Correo.builder()
                    .persona(Persona.builder().personaId(datos.getNovelId()).build())
                    .correoDescripcion(datos.getCorreo())
                    .correoEstado("01")
                    .build();
            correoRepository.save(nuevoCorreo);
        }

        if (datos.getTelefonoId() != null) {
            telefonoRepository.updateTelefonoByPersonaId(datos.getNovelId(), datos.getTelefono());
        } else {
            Telefono nuevoTelefono = Telefono.builder()
                    .persona(Persona.builder().personaId(datos.getNovelId()).build())
                    .telefonoDescripcion(datos.getTelefono())
                    .telefonoEstado("01")
                    .build();
            telefonoRepository.save(nuevoTelefono);
        }

        if (datos.getDirectorId() != null) {
            Optional<Persona> director = personaRepository.findById(datos.getDirectorId());

            if (director.isPresent()) {
                Persona directorPersona = director.get();
                directorPersona.setPersonaNombres(datos.getNombresDirector());
                directorPersona.setPersonaApellidos(datos.getApellidosDirector());
                personaRepository.save(directorPersona);
            }
        } else {
            SecureRandom secureRandom = new SecureRandom();

            Persona nuevoDirector = Persona.builder()
                    .personaNombres(datos.getNombresDirector())
                    .personaApellidos(datos.getApellidosDirector())
                    .personaTipoDocumentoId(1)
                    .personaTipoDocumentoValor(String.valueOf(10000000 + secureRandom.nextInt(90000000)))
                    .personaGenero("01")
                    .personaEstado("01")
                    .build();
            Persona respuesta = personaRepository.save(nuevoDirector);

            Director nuevo = Director.builder()
                    .persona(respuesta)
                    .build();
            directorRepository.save(nuevo);

            Optional<InstitucionEducativa> institucion = institucionEducativaRepository.findById(datos.getInstitucionId());
            if (institucion.isPresent()) {
                InstitucionEducativa institucionEducativa = institucion.get();
                institucionEducativa.setDirector(nuevo);
                institucionEducativaRepository.save(institucionEducativa);
            }
        }

        Optional<Novel> novelOpt = novelRepository.findById(datos.getNovelId());
        if (novelOpt.isPresent()) {
            Novel novel = novelOpt.get();
            novel.setInstitucionEducativa(InstitucionEducativa.builder().institucionEducativaId(datos.getInstitucionId()).build());
            novelRepository.save(novel);
        }

        if (datos.getCorreoDirectorId() != null) {
            correoRepository.updateCorreoByPersonaId(datos.getDirectorId(), datos.getCorreoDirector());
        } else {
            Correo nuevoCorreo = Correo.builder()
                    .persona(Persona.builder().personaId(datos.getDirectorId()).build())
                    .correoDescripcion(datos.getCorreoDirector())
                    .correoEstado("01")
                    .build();
            correoRepository.save(nuevoCorreo);
        }

        if (datos.getTelefonoDirectorId() != null) {
            telefonoRepository.updateTelefonoByPersonaId(datos.getDirectorId(), datos.getTelefonoDirector());
        } else {
            Telefono nuevoTelefono = Telefono.builder()
                    .persona(Persona.builder().personaId(datos.getDirectorId()).build())
                    .telefonoDescripcion(datos.getTelefonoDirector())
                    .telefonoEstado("01")
                    .build();
            telefonoRepository.save(nuevoTelefono);
        }

        return "Datos actualizados correctamente";
    }
}
