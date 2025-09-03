package com.app.springboot.formulario;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
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

    @Autowired
    private VerificacionRepository verificacionRepository;

    public Novel getInfo(String dni) {
        return novelRepository.findByPersonaTipoDocumentoValor(dni).orElse(null);
    }

    @Transactional
    public String updateInfo(FormularioData datos) {
        Optional<Correo> existingCorreo = correoRepository.findByCorreoDescripcionAndPersona_PersonaIdNot(
                datos.getCorreo(), datos.getNovelId());
        if (existingCorreo.isPresent()) {
            return "Error: El correo ingresado para el novel ya está registrado para la persona " +
                    existingCorreo.get().getPersona().getPersonaApellidos() + " " + existingCorreo.get().getPersona().getPersonaNombres();
        }

        Optional<Telefono> existingTelefono = telefonoRepository.findByTelefonoDescripcionAndPersona_PersonaIdNot(
                datos.getTelefono(), datos.getNovelId());
        if (existingTelefono.isPresent()) {
            return "Error: El teléfono ingresado para el novel ya está registrado para la persona " +
                    existingTelefono.get().getPersona().getPersonaApellidos() + " " + existingTelefono.get().getPersona().getPersonaNombres();
        }

        Director directorGuardado = null;

        if (datos.getDirectorId() == null) {
            SecureRandom secureRandom = new SecureRandom();
            Persona nuevaPersona = Persona.builder()
                    .personaNombres(datos.getNombresDirector())
                    .personaApellidos(datos.getApellidosDirector())
                    .personaTipoDocumentoId(1)
                    .personaTipoDocumentoValor(String.valueOf(10000000 + secureRandom.nextInt(90000000)))
                    .personaGenero("01")
                    .personaEstado("01")
                    .build();
            Persona respuesta = personaRepository.save(nuevaPersona);
            Director nuevoDirector = Director.builder().persona(respuesta).build();
            directorGuardado = directorRepository.save(nuevoDirector);

            Correo nuevoCorreo = Correo.builder()
                    .persona(directorGuardado.getPersona())
                    .correoDescripcion(datos.getCorreoDirector())
                    .correoEstado("01")
                    .build();
            correoRepository.save(nuevoCorreo);

            Telefono nuevoTelefono = Telefono.builder()
                    .persona(directorGuardado.getPersona())
                    .telefonoDescripcion(datos.getTelefonoDirector())
                    .telefonoEstado("01")
                    .build();
            telefonoRepository.save(nuevoTelefono);
        } else {
            Optional<Persona> personaUsuarioOpt = personaRepository.findById(datos.getDirectorId());
            if (personaUsuarioOpt.isEmpty()) {
                return "Error: No se encontró el director con ID " + datos.getNovelId();
            }

            Optional<Director> directorOpt = directorRepository.findByPersona_PersonaId(personaUsuarioOpt.get().getPersonaId());
            if (directorOpt.isEmpty()) {
                return "Error: No se encontró el director asociado a la persona con ID " + personaUsuarioOpt.get().getPersonaId();
            }

            directorGuardado = directorOpt.get();

            Persona directorPersona = personaUsuarioOpt.get();
            directorPersona.setPersonaNombres(datos.getNombresDirector());
            directorPersona.setPersonaApellidos(datos.getApellidosDirector());
            personaRepository.save(directorPersona);

            if (datos.getCorreoDirectorId() != null) {
                correoRepository.updateCorreoByPersonaId(personaUsuarioOpt.get().getPersonaId(), datos.getCorreoDirector());
            } else {
                Correo nuevoCorreo = Correo.builder()
                        .persona(directorPersona)
                        .correoDescripcion(datos.getCorreoDirector())
                        .correoEstado("01")
                        .build();
                correoRepository.save(nuevoCorreo);
            }

            if (datos.getTelefonoDirectorId() != null) {
                telefonoRepository.updateTelefonoByPersonaId(personaUsuarioOpt.get().getPersonaId(), datos.getTelefonoDirector());
            } else {
                Telefono nuevoTelefono = Telefono.builder()
                        .persona(directorPersona)
                        .telefonoDescripcion(datos.getTelefonoDirector())
                        .telefonoEstado("01")
                        .build();
                telefonoRepository.save(nuevoTelefono);
            }
        }

        if (datos.getInstitucionId() == null) {
            return "Error: La institución educativa es obligatoria.";
        } else {
            Optional<InstitucionEducativa> institucionOpt = institucionEducativaRepository.findById(datos.getInstitucionId());
            if (institucionOpt.isEmpty()) {
                return "Error: No se encontró la institución educativa con ID " + datos.getInstitucionId();
            }
            InstitucionEducativa institucion = institucionOpt.get();
            institucion.setDirector(directorGuardado);
            institucionEducativaRepository.save(institucion);
        }

        Optional<Persona> personaUsuarioOpt = personaRepository.findById(datos.getNovelId());
        if (personaUsuarioOpt.isEmpty()) {
            return "Error: No se encontró el novel con ID " + datos.getNovelId();
        }

        Persona personaUsuario = personaUsuarioOpt.get();

        if (datos.getCorreoId() != null) {
            correoRepository.updateCorreoByPersonaId(datos.getNovelId(), datos.getCorreo());
        } else {
            Correo nuevoCorreo = Correo.builder()
                    .persona(personaUsuario)
                    .correoDescripcion(datos.getCorreo())
                    .correoEstado("01")
                    .build();
            correoRepository.save(nuevoCorreo);
        }

        if (datos.getTelefonoId() != null) {
            telefonoRepository.updateTelefonoByPersonaId(datos.getNovelId(), datos.getTelefono());
        } else {
            Telefono nuevoTelefono = Telefono.builder()
                    .persona(personaUsuario)
                    .telefonoDescripcion(datos.getTelefono())
                    .telefonoEstado("01")
                    .build();
            telefonoRepository.save(nuevoTelefono);
        }

        Optional<Novel> novelOpt = novelRepository.findByPersona_PersonaId(personaUsuarioOpt.get().getPersonaId());
        if (novelOpt.isEmpty()) {
            return "Error: No se encontró el novel asociado a la persona con ID " + personaUsuarioOpt.get().getPersonaId();
        }

        Novel novel = novelOpt.get();
        novel.setInstitucionEducativa(InstitucionEducativa.builder().institucionEducativaId(datos.getInstitucionId()).build());
        novelRepository.save(novel);

        Optional<Verificacion> verificacionOpt = verificacionRepository.findByNovel_NovelId(novelOpt.get().getNovelId());
        if (verificacionOpt.isPresent()) {
            Verificacion verificacionExistente = verificacionOpt.get();
            verificacionExistente.setVerificacionObservaciones(datos.getObservaciones());
            verificacionExistente.setVerificacionFechaModificacion(LocalDateTime.now());
            verificacionRepository.save(verificacionExistente);
            return "Datos actualizados correctamente";
        } else {
            Verificacion nuevaVerificacion = Verificacion.builder()
                    .novel(novelOpt.get())
                    .verificacionObservaciones(datos.getObservaciones())
                    .verificacionFechaCreacion(LocalDateTime.now())
                    .build();
            verificacionRepository.save(nuevaVerificacion);
            return "Datos actualizados correctamente";
        }
    }
}
