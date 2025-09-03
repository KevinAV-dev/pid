package com.app.springboot.formulario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PID_NOVEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Novel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOVEL_ID")
    private Long novelId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NOVEL_PERSONA_ID", referencedColumnName = "PERSONA_ID", nullable = false)
    private Persona persona;

    @Column(name = "NOVEL_GRUPO_INSCRIPCION_ID")
    private Integer novelGrupoInscripcionId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NOVEL_INSTITUCION_EDUCATIVA_ID", referencedColumnName = "INSTITUCION_EDUCATIVA_ID", nullable = false)
    private InstitucionEducativa institucionEducativa;

    @OneToOne(mappedBy = "novel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Verificacion verificacion;

    @Column(name = "NOVEL_USUARIO_CREACION")
    private Integer novelUsuarioCreacion;

    @Column(name = "NOVEL_FECHA_CREACION", updatable = false)
    private LocalDateTime novelFechaCreacion;

    @Column(name = "NOVEL_USUARIO_MODIFICACION")
    private Integer novelUsuarioModificacion;

    @Column(name = "NOVEL_FECHA_MODIFICACION")
    private LocalDateTime novelFechaModificacion;
}
