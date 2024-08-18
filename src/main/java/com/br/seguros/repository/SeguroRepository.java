package com.br.seguros.repository;



import com.br.seguros.model.SeguroModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepository extends MongoRepository<SeguroModel, String> {

   SeguroModel findByTipoSeguro(String tipoSeguro);

}