package com.api.senati.DTO;

import lombok.Data;

@Data
public class GrupoDTO {
    private String nombre;
    private String descripcion;
    private String observacion;
    private String estado;
    private String urlPlantilla;
    private Integer idTipo;
}
