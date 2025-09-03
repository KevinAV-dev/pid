package com.app.springboot.formulario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PID_DIRECTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIRECTOR_ID")
    private Long directorId;

    @Column(name = "DIRECTOR_USUARIO_CREACION")
    private Integer directorUsuarioCreacion;

    @Column(name = "DIRECTOR_FECHA_CREACION", updatable = false)
    private LocalDateTime directorFechaCreacion;

    @Column(name = "DIRECTOR_USUARIO_MODIFICACION")
    private Integer directorUsuarioModificacion;

    @Column(name = "DIRECTOR_FECHA_MODIFICACION")
    private LocalDateTime directorFechaModificacion;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DIRECTOR_PERSONA_ID", referencedColumnName = "PERSONA_ID", nullable = false)
    private Persona persona;
}
