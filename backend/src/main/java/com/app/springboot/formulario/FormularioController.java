package com.app.springboot.formulario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/formulario")
public class FormularioController {

    @Autowired
    FormularioServicio formularioServicio;

    @Autowired
    InstitucionEducativaServicio institucionEducativaServicio;

    @GetMapping("/info/{dni}")
    public ResponseEntity<Novel> obtenerInfo(@PathVariable String dni) {
        return ResponseEntity.ok(formularioServicio.getInfo(dni));
    }

    @GetMapping("/instituciones")
    public ResponseEntity<?> obtenerInstituciones() {
        return ResponseEntity.ok(institucionEducativaServicio.getInstitucionesEducativas());
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> recibirDatos(@RequestBody FormularioData datos) {
        try {
            String mensaje = formularioServicio.updateInfo(datos);

            if (mensaje.startsWith("Error:")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensaje", mensaje));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensaje", mensaje));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensaje", "Error interno del servidor"));
        }
    }
}
