package com.app.springboot.formulario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formulario")
//@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<Void> recibirDatos(@RequestBody FormularioData datos) {
        try {
            String mensaje = formularioServicio.updateInfo(datos);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
