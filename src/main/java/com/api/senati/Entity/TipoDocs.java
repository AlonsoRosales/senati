package com.api.senati.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cloud_tipo")
@Data
public class TipoDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ti_idtipo")
    private Integer idTipo;
    @Column(name = "ti_tipo")
    private String tipo;
    @Column(name = "ti_observacion")
    private String observacion;
    @Column(name = "ti_datireg")
    private Timestamp registro;
    @Column(name = "ti_regpor")
    private Integer regpor;
    @Column(name = "ti_datimod")
    private Timestamp modificado;
    @Column(name = "ti_modpor")
    private Integer modpor;
    @Column(name = "ti_estado")
    private String estado;
}
