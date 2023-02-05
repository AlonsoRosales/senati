package com.api.senati.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "cloud_firmante")
@Data
public class Firmante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fi_idfirmante")
    private Integer idFirmante;
    @NotNull(message = "Es un campo obligatorio.")
    @Column(name = "fi_iddependencia")
    private Integer idDependencia;
    @NotNull(message = "Es un campo obligatorio.")
    @Size(min = 8,max = 8,message = "El documento de identidad tiene un tamaño de 8.")
    @Column(name = "fi_docide",unique = true)
    private String docide;
    @NotNull(message = "Es un campo obligatorio.")
    @Column(name = "fi_nombre")
    private String nombre;
    @Email(message = "Este campo debe respetar el formato de un correo.")
    @NotNull(message = "Es un campo obligatorio.")
    @Column(name = "fi_correo",unique = true)
    private String correo;
    @Email(message = "Este campo debe respetar el formato de un correo.")
    @Column(name = "fi_correoa",unique = true)
    private String correoA;
    @Column(name = "fi_descripcion")
    private String descripcion;
    @Column(name = "fi_observacion")
    private String observacion;
    @Column(name = "fi_repositorio")
    private String repositorio;
    @Column(name = "fi_firma")
    private String firma;
    @Column(name = "fi_password")
    private String password;
    @Column(name = "fi_datireg")
    private Timestamp registro;
    @Column(name = "fi_regpor")
    private Integer regpor;
    @Column(name = "fi_datimod")
    private Timestamp modificado;
    @Column(name = "fi_modpor")
    private Integer modpor;
    @Column(name = "fi_estado")
    private String estado;
    @Column(name = "fi_cargo")
    private String cargo;
    @Column(name = "fi_sexo")
    private String sexo;
    @Column(name = "fi_foto")
    private String foto;
    @Column(name = "fi_rol")
    private String rol;
}
