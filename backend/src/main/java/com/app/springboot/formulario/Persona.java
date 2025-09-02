package com.app.springboot.formulario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PID_PERSONA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSONA_ID")
    private Long personaId;

    @Column(name = "PERSONA_TIPO_DOCUMENTO_ID")
    private Integer personaTipoDocumentoId;

    @Column(name = "PERSONA_TIPO_DOCUMENTO_VALOR", unique = true, length = 20, nullable = false)
    private String personaTipoDocumentoValor;

    @Column(name = "PERSONA_NOMBRES", length = 300, nullable = false)
    private String personaNombres;

    @Column(name = "PERSONA_APELLIDOS", length = 300, nullable = false)
    private String personaApellidos;

    @Column(name = "PERSONA_GENERO", length = 2, nullable = false)
    private String personaGenero;

    @Column(name = "PERSONA_DIRECCION", length = 300)
    private String personaDireccion;

    @Column(name = "PERSONA_FECHA_NACIMIENTO")
    private LocalDate personaFechaNacimiento;

    @Column(name = "PERSONA_ESTADO", length = 2, nullable = false)
    private String personaEstado;

    @Column(name = "PERSONA_USUARIO_CREACION")
    private Integer personaUsuarioCreacion;

    @Column(name = "PERSONA_FECHA_CREACION", updatable = false)
    private LocalDateTime personaFechaCreacion;

    @Column(name = "PERSONA_USUARIO_MODIFICACION")
    private Integer personaUsuarioModificacion;

    @Column(name = "PERSONA_FECHA_MODIFICACION")
    private LocalDateTime personaFechaModificacion;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Correo> correos;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Telefono> telefonos;

}
