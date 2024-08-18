package com.br.seguros.repository;

import com.br.seguros.model.SeguroContratadoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroContratadoRepository extends MongoRepository<SeguroContratadoModel, String> {


}