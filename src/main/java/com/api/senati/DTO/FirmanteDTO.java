package com.api.senati.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
public class FirmanteDTO {
    @Column(unique = true)
    private String docide;
    private String nombre;
    @Email(message = "Este campo debe respetar el formato de un correo.")
    @Column(unique = true)
    private String correo;
    @Email(message = "Este campo debe respetar el formato de un correo.")
    @Column(unique = true)
    private String correoA;
    private String repositorio;
    private String firma;
    private String password;
    private Integer modpor;
    private String estado;
    private String cargo;
    private String rol;

}
