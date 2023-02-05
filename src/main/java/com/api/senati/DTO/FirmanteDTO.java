package com.api.senati.DTO;
import lombok.Data;
import javax.persistence.Column;
import javax.validation.constraints.Email;

@Data
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
