package com.api.senati.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "cloud_grupo")
@Data
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gr_idgrupo")
    private Integer idGrupo;
    @Column(name = "gr_iddependencia")
    @NotNull(message = "Es un campo obligatorio.")
    @NotBlank(message = "Este campo no puede ser vacío.")
    private Integer idDependencia;
    @Column(name = "gr_idformato")
    @NotNull(message = "Es un campo obligatorio.")
    @NotBlank(message = "Este campo no puede ser vacío.")
    private Integer idFormato;
    @Column(name = "gr_nombre")
    @NotNull(message = "Es un campo obligatorio.")
    @NotBlank(message = "Este campo no puede ser vacío.")
    private String nombre;
    @Column(name = "gr_fechaini")
    private Date inicio;
    @Column(name = "gr_fechafin")
    private Date fin;
    @Column(name = "gr_descripcion")
    private String descripcion;
    @Column(name = "gr_observacion")
    private String observacion;
    @Column(name = "gr_datireg")
    private Timestamp registro;
    @Column(name = "gr_regpor")
    private Integer regpor;
    @Column(name = "gr_datimod")
    private Timestamp modificado;
    @Column(name = "gr_modpor")
    private Integer modpor;
    @Column(name = "gr_estado")
    private String estado;
    @Column(name = "gr_urlplantilla")
    private String urlPlantilla;
    @Column(name = "gr_idtipo")
    @NotNull(message = "Es un campo obligatorio.")
    @NotBlank(message = "Este campo no puede ser vacío.")
    private Integer idTipo;
}