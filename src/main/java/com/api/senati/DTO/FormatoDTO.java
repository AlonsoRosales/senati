package com.api.senati.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class FormatoDTO {
    private String nombre;
    private String descripcion;
    private String observacion;
    private Integer alto;
    private Integer ancho;
    private String tipo;
}
