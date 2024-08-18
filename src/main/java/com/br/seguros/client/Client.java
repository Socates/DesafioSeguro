package com.br.seguros.client;

import com.br.seguros.dto.ErroDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.logging.Logger;

@Component
public class Client {

    private final WebClient webClient;
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private final Gson gson = new Gson();

    public Client(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getRequest(String url) {
        return getRequest(url, 3);
    }

    public String getRequest(String url, int maxRetries) {

        try {

            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        ErroDTO errorResponse = parseErrorResponse(errorBody);
                                        return Mono.error(new WebClientResponseException(
                                                errorResponse.getMessage(),
                                                errorResponse.getStatus(),
                                                errorResponse.getMessage(),
                                                null,
                                                null,
                                                null
                                        ));
                                    }))
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(maxRetries, Duration.ofSeconds(2))
                            .filter(this::isRetryableException))
                    .doOnError(e -> logger.severe("GET request - Erro: " + e.getMessage()))
                    .block();
        } catch (Exception e) {
            logger.severe("GET request - Exceção: " + e.getMessage());
            return "erro: " + e.getMessage();
        }
    }

    private boolean isRetryableException(Throwable throwable) {
        if (throwable instanceof WebClientResponseException webClientException) {
            return webClientException.getStatusCode().is5xxServerError();
        }
        return throwable instanceof DataBufferLimitException;
    }

    private ErroDTO parseErrorResponse(String errorBody) {
        try {
            return gson.fromJson(errorBody, ErroDTO.class);
        } catch (JsonSyntaxException e) {

            logger.severe("Erro ao deserializar resposta de erro com Gson: " + e.getMessage());
            return new ErroDTO();
        }
    }
}
