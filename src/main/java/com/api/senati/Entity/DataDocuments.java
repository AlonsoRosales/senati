package com.api.senati.Entity;

import lombok.Data;

import java.util.List;
@Data
public class DataDocuments {
    private String idAlum;
    private String nombres;
    private String apellidos;
    private String dni;
    private String carrera;
    private String nivel;
    private String duracion;
    private String puesto;
    private String promedio;
    private String sede;
    private String merito;
    private String foto;//por revisar si es en base64 o multipart

    // FIRMANTE
    private String nombrefir;
    private String cargofir;
    private List<Curso> cursos;

    // TIPO DOCUMENTAL
    private String emision;
    private String cod;

    //DIPLOMADO
    private String diplomado;
}
