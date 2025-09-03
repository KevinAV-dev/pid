package com.app.springboot.formulario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;

@Entity
@Table(name = "PID_INSTITUCION_EDUCATIVA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitucionEducativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTITUCION_EDUCATIVA_ID")
    private Long institucionEducativaId;

    @Column(name = "INSTITUCION_EDUCATIVA_UGEL_ID")
    private Integer institucionEducativaUgelId;

    @Column(name = "INSTITUCION_EDUCATIVA_CODIGO_MODULAR", unique = true, length = 10, nullable = false)
    private String institucionEducativaCodigoModular;

    @Column(name = "INSTITUCION_EDUCATIVA_NOMBRE", length = 300)
    private String institucionEducativaNombre;

    @Column(name = "INSTITUCION_EDUCATIVA_TIPO", length = 20, nullable = false)
    private String institucionEducativaTipo;

    @Column(name = "INSTITUCION_EDUCATIVA_ESTADO", length = 2, nullable = false)
    private String institucionEducativaEstado;

    @Column(name = "INSTITUCION_EDUCATIVA_USUARIO_CREACION")
    private Integer institucionEducativaUsuarioCreacion;

    @Column(name = "INSTITUCION_EDUCATIVA_FECHA_CREACION", updatable = false)
    private LocalDateTime institucionEducativaFechaCreacion;

    @Column(name = "INSTITUCION_EDUCATIVA_USUARIO_MODIFICACION")
    private Integer institucionEducativaUsuarioModificacion;

    @Column(name = "INSTITUCION_EDUCATIVA_FECHA_MODIFICACION")
    private LocalDateTime institucionEducativaFechaModificacion;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTITUCION_EDUCATIVA_DIRECTOR_ID", referencedColumnName = "DIRECTOR_ID")
    @JsonManagedReference
    private Director director;
}
