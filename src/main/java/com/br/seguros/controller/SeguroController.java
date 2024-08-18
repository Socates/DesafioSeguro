package com.br.seguros.controller;

import com.br.seguros.dto.SimulacaoDTO;
import com.br.seguros.model.SeguroContratadoModel;
import com.br.seguros.model.SeguroSimuladoModel;
import com.br.seguros.model.SeguroModel;
import com.br.seguros.service.SeguroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("seguros")
public class SeguroController {

    private final SeguroService seguroService;
    private static final Logger logger = Logger.getLogger(SeguroController.class.getName());

    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }

    @PostMapping("/contratar/{id}")
    @Operation(summary = "Contratar um seguro", description = "Contrata um seguro com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro contratado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao contratar seguro")
    })
    public ResponseEntity<Object> contratarSeguro(@PathVariable String id) {
        try {
            SeguroContratadoModel seguroContratado = seguroService.contratarSeguro(id);
            return ResponseEntity.ok(seguroContratado);
        } catch (Exception e) {
            logger.severe("Erro ao contratar seguro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao contratar seguro: " + e.getMessage());
        }
    }

    @GetMapping("/simular")
    @Operation(summary = "Simular um seguro", description = "Simula um seguro com base nos dados fornecidos na solicitação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao simular seguro")
    })
    public ResponseEntity<Object> simularSeguro(@RequestBody SimulacaoDTO simulacao) {
        try {
            SeguroSimuladoModel seguroSimulado = seguroService.simular(simulacao);
            return ResponseEntity.ok(seguroSimulado);
        } catch (Exception e) {
            logger.severe("Erro ao simular seguro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao simular seguro: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar seguros", description = "Obtém uma lista de todos os seguros disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de seguros retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao listar seguros")
    })
    public ResponseEntity<List<SeguroModel>> listarSeguros() {
        List<SeguroModel> seguros = seguroService.listarClientes();
        return ResponseEntity.ok(seguros);
    }

}
