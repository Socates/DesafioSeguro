package com.br.seguros.service;

import com.br.seguros.client.Client;
import com.br.seguros.dto.ClienteDTO;
import com.br.seguros.dto.SimulacaoDTO;
import com.br.seguros.model.SeguroSimuladoModel;
import com.br.seguros.model.SeguroContratadoModel;
import com.br.seguros.model.SeguroModel;
import com.br.seguros.repository.SeguroContratadoRepository;
import com.br.seguros.repository.SeguroRepository;
import com.br.seguros.repository.SeguroSimuladoRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

@Service
public class SeguroService {

    private final Client client;
    private final SeguroRepository seguroRepository;
    private final SeguroContratadoRepository seguroContratadoRepository;
    private final SeguroSimuladoRepository seguroSimuladoRepository;

    private final Logger logger = Logger.getLogger(SeguroService.class.getName());
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final Gson gson = new Gson();

    //

    public SeguroService(Client client, SeguroRepository seguroRepository, SeguroContratadoRepository seguroContratadoRepository, SeguroSimuladoRepository seguroSimuladoRepository) {

        this.client = client;
        this.seguroRepository = seguroRepository;
        this.seguroContratadoRepository = seguroContratadoRepository;
        this.seguroSimuladoRepository = seguroSimuladoRepository;
    }


    public SeguroContratadoModel contratarSeguro(String id) throws Exception {

        SeguroSimuladoModel seguroSimuladoModel = buscarSimulacaoSeguroByID(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLimite = LocalDate.parse(seguroSimuladoModel.getDataValidade(), formatter);
        LocalDate dataAtual = LocalDate.now();

        if (dataLimite.isBefore(dataAtual)) {
            throw new Exception("Seguro inválido. Data de validade já passou.");
        }

        String dataContrato = dataAtual.format(formatter);

        SeguroContratadoModel seguroContratadoModel = new SeguroContratadoModel();
        seguroContratadoModel.setIdSeguro(seguroSimuladoModel.getIdSeguro());
        seguroContratadoModel.setIdCliente(seguroSimuladoModel.getIdCliente());
        seguroContratadoModel.setValorSeguro(seguroSimuladoModel.getValor());
        seguroContratadoModel.setDataContrato(dataContrato);

        return seguroContratadoRepository.save(seguroContratadoModel);
    }


    public SeguroSimuladoModel simular(SimulacaoDTO simulacao) throws Exception {

        String response = client.getRequest("cliente/cpf/" + simulacao.getCpf());

        if (response.contains("erro")) {
            throw new Exception(response);
        }

        ClienteDTO clienteDTO = gson.fromJson(response, ClienteDTO.class);

        String dataSimulacao = sdf.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String validade = sdf.format(calendar.getTime());

        SeguroModel seguroModel = buscarSeguro(simulacao.getTipoSeguro());

        SeguroSimuladoModel seguroSimuladoModel = new SeguroSimuladoModel(seguroModel.getId(), clienteDTO.getId(), seguroModel.getTipoSeguro(), seguroModel.getValor(), dataSimulacao, validade);

        seguroSimuladoModel = seguroSimuladoRepository.save(seguroSimuladoModel);

        return seguroSimuladoModel;
    }


    public SeguroModel buscarSeguro(String tipo) throws Exception {

        SeguroModel seguro = seguroRepository.findByTipoSeguro(tipo);
        if (seguro == null) {
            logger.info("Seguro não encontrado: " + tipo);
            throw new Exception("Seguro não encontrado: " + tipo);
        }

        return seguro;
    }


    public SeguroSimuladoModel buscarSimulacaoSeguroByID(String id) throws Exception {

        Optional<SeguroSimuladoModel> seguro = seguroSimuladoRepository.findById(id);
        if (seguro.isEmpty()) {
            logger.info("Seguro não encontrado: " + id);
            throw new Exception("Seguro não encontrado: " + id);
        }

        return seguro.get();
    }


    public List<SeguroModel> listarClientes() {
        return seguroRepository.findAll();
    }

}