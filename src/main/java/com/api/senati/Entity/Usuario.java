package com.api.senati.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "cloud_usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_idusuario")
    private Integer idUsuario;
    @Column(name = "us_usuario")
    private String usuario;
    @Column(name = "us_clave")
    private String password;
    @Column(name = "us_nivel")
    private String nivel;
    @Column(name = "us_vigencia")
    private Date vigencia;
    @Column(name = "us_observacion")
    private String observacion;
    @Column(name = "us_datireg")
    private Timestamp datireg;
    @Column(name = "us_datimod")
    private Timestamp datimod;
    @Column(name = "us_regpor")
    private Integer regpor;
    @Column(name = "us_modpor")
    private Integer modpor;
    @Column(name = "us_estado")
    private String estado;
}
