package com.api.senati.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cloud_formato")
@Data
public class Formato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fo_idformato")
    private Integer idFormato;
    @Column(name = "fo_iddependencia")
    private Integer idDependencia;
    @Column(name = "fo_nombre")
    private String nombre;
    @Column(name = "fo_descripcion")
    private String descripcion;
    @Column(name = "fo_observacion")
    private String observacion;
    @Column(name = "fo_datireg")
    private Timestamp registro;
    @Column(name = "fo_regpor")
    private Integer regpor;
    @Column(name = "fo_datimod")
    private Timestamp modificado;
    @Column(name = "fo_modpor")
    private Integer modpor;
    @Column(name = "fo_estado")
    private String estado;
    @Column(name = "fo_alto")
    private Integer alto;
    @Column(name = "fo_ancho")
    private Integer ancho;
    @Column(name = "fo_tipo")
    private String tipo;
}
