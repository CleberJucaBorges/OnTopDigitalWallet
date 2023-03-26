package com.ontopchallenge.ontopdigitalwallet.Infra;
import com.ontopchallenge.ontopdigitalwallet.Dto.BankProvider.BankProviderRequestDTO;
import com.ontopchallenge.ontopdigitalwallet.Dto.BankProvider.BankProviderResponseDTO;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class BankTransferHttpRequest {
    private final Environment environment;
    public BankTransferHttpRequest(Environment environment) {
        this.environment = environment;
    }
    public String getApiUrl() {
        return environment.getProperty("api.bank_provider");
    }

    public BankProviderResponseDTO doTransferRequest(@RequestBody BankProviderRequestDTO transferRequest) {
        String uri = getApiUrl() + "/api/v1/payments";
        WebClient webClient = WebClient.builder()
                .baseUrl(uri)
                .build();

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transferRequest)
                .retrieve()
                .bodyToMono(BankProviderResponseDTO.class)
                .block();

    }
}
