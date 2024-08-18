package com.br.seguros.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seguroSimulado")
public class SeguroSimuladoModel {

    @Id
    private String id;

    private String idSeguro;
    private String idCliente;
    private String tipoSeguro;
    private String valor;
    private String dataSimulacao;
    private String dataValidade;


    public SeguroSimuladoModel(String idSeguro, String idCliente, String tipoSeguro, String valor, String dataSimulacao, String dataValidade) {
        this.idSeguro = idSeguro;
        this.idCliente = idCliente;
        this.tipoSeguro = tipoSeguro;
        this.valor = valor;
        this.dataSimulacao = dataSimulacao;
        this.dataValidade = dataValidade;
    }

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

    public String getDataSimulacao() {
        return dataSimulacao;
    }

    public void setDataSimulacao(String dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }
}