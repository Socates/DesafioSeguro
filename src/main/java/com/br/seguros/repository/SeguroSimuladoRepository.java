package com.br.seguros.repository;

import com.br.seguros.model.SeguroSimuladoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroSimuladoRepository  extends MongoRepository<SeguroSimuladoModel, String> {

}