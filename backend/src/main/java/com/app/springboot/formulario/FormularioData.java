package com.app.springboot.formulario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioData {
    private Long novelId;
    private Long correoId;
    private String correo;
    private Long telefonoId;
    private String telefono;
    private Long institucionId;
    private Long directorId;
    private String nombresDirector;
    private String apellidosDirector;

    private Long correoDirectorId;
    private String correoDirector;
    private Long telefonoDirectorId;
    private String telefonoDirector;

    private String observaciones;
}
