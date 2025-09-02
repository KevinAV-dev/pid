package com.app.springboot.formulario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PID_CORREO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Correo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CORREO_ID")
    private Integer correoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CORREO_PERSONA_ID", referencedColumnName = "PERSONA_ID", nullable = false)
    @JsonBackReference
    private Persona persona;

    @Column(name = "CORREO_DESCRIPCION", length = 300, unique = true, nullable = false)
    private String correoDescripcion;

    @Column(name = "CORREO_ESTADO", length = 2, nullable = false)
    private String correoEstado;

    @Column(name = "CORREO_USUARIO_CREACION")
    private Integer correoUsuarioCreacion;

    @Column(name = "CORREO_FECHA_CREACION", updatable = false)
    private LocalDateTime correoFechaCreacion;

    @Column(name = "CORREO_USUARIO_MODIFICACION")
    private Integer correoUsuarioModificacion;

    @Column(name = "CORREO_FECHA_MODIFICACION")
    private LocalDateTime correoFechaModificacion;
}
