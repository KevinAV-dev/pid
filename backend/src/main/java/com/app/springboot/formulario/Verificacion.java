package com.app.springboot.formulario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PID_VERIFICACION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Verificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VERIFICACION_ID")
    private Long verificacionId;

    @Column(name = "VERIFICACION_OBSERVACIONES", columnDefinition = "TEXT")
    private String verificacionObservaciones;

    @Column(name = "VERIFICACION_USUARIO_CREACION")
    private Integer verificacionUsuarioCreacion;

    @Column(name = "VERIFICACION_FECHA_CREACION", updatable = false)
    private LocalDateTime verificacionFechaCreacion;

    @Column(name = "VERIFICACION_USUARIO_MODIFICACION")
    private Integer verificacionUsuarioModificacion;

    @Column(name = "VERIFICACION_FECHA_MODIFICACION")
    private LocalDateTime verificacionFechaModificacion;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VERIFICACION_NOVEL_ID", referencedColumnName = "NOVEL_ID", nullable = false)
    @JsonBackReference
    private Novel novel;

}

