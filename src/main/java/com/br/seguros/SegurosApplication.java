package com.br.seguros;

import com.br.seguros.model.SeguroModel;
import com.br.seguros.repository.SeguroRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SegurosApplication {

	@Autowired
	private SeguroRepository seguroRepository;

	public static void main(String[] args) {
		SpringApplication.run(SegurosApplication.class, args);
	}


	@PostConstruct
	public void init() {


		List<SeguroModel> seguros = Arrays.asList(
				new SeguroModel("1", "Bronze", "10.00"),
				new SeguroModel("2", "Prata", "20.00"),
				new SeguroModel("3", "Ouro", "30.00")
		);

		for (SeguroModel seguro : seguros) {
			Optional<SeguroModel> existingSeguro = seguroRepository.findById(seguro.getId());

			if (existingSeguro.isEmpty()) {
				seguroRepository.save(seguro);
				System.out.println("Novo seguro adicionado ao banco de dados: " + seguro.getTipoSeguro());
			} else {
				System.out.println("Seguro já existe no banco de dados: " + existingSeguro);
			}
		}
	}


}