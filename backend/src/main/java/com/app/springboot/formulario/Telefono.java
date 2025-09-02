package com.app.springboot.formulario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PID_TELEFONO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TELEFONO_ID")
    private Integer telefonoId;

    // Relación ManyToOne: muchos teléfonos pueden pertenecer a una Persona.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TELEFONO_PERSONA_ID", referencedColumnName = "PERSONA_ID", nullable = false)
    @JsonBackReference
    private Persona persona;

    @Column(name = "TELEFONO_DESCRIPCION", length = 9, unique = true, nullable = false)
    private String telefonoDescripcion;

    @Column(name = "TELEFONO_ESTADO", length = 2, nullable = false)
    private String telefonoEstado;

    @Column(name = "TELEFONO_USUARIO_CREACION")
    private Integer telefonoUsuarioCreacion;

    @Column(name = "TELEFONO_FECHA_CREACION", updatable = false)
    private LocalDateTime telefonoFechaCreacion;

    @Column(name = "TELEFONO_USUARIO_MODIFICACION")
    private Integer telefonoUsuarioModificacion;

    @Column(name = "TELEFONO_FECHA_MODIFICACION")
    private LocalDateTime telefonoFechaModificacion;

}
