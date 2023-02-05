package com.api.senati.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
@Data
public class UsuarioDTO {
    private String usuario;
    private String password;
    private String nivel;
    private Date vigencia;
    private String observacion;
    private String estado;
}
