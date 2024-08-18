package com.br.seguros.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seguro")
public class SeguroModel {

    @Id
    private String id;
    private String tipoSeguro;
    private String valor;

    public SeguroModel(String id, String tipoSeguro, String valor) {
        this.id = id;
        this.tipoSeguro = tipoSeguro;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}