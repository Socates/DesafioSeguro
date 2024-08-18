package com.br.seguros.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seguroContratado")
public class SeguroContratadoModel {

    @Id
    private String id;

    private String idSeguro;
    private String idCliente;
    private String valorSeguro;
    private String dataContrato;

    //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(String idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(String valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public String getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(String dataContrato) {
        this.dataContrato = dataContrato;
    }
}