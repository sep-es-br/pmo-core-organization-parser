package br.gov.es.pmo.organization_parser.pmo_base.utils;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ApiClient {

    private final WebClient webClient;

    public ApiClient(String baseUrl) {
        this.webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

    private Mono<String> execute(
        String url,
        String token,
        HttpMethod method,
        Object body
    ) {

        WebClient.RequestBodySpec request =
            this.webClient
                .method(method)
                .uri(url)
                .header("Authorization", "Bearer " + token);

        WebClient.ResponseSpec response =
            (body != null)
                ? request.bodyValue(body).retrieve()
                : request.retrieve();

        return response.bodyToMono(String.class);
    }

    public Mono<String> doGetRequest(
        String url,
        String token
    ) {
        return execute(url, token, HttpMethod.GET, null);
    }

    public Mono<String> doPostRequest(
        String url,
        Object body,
        String token
    ) {
        return execute(url, token, HttpMethod.POST, body);
    }

    public Mono<String> doPutRequest(
        String url,
        Object body,
        String token
    ) {
        return execute(url, token, HttpMethod.PUT, body);
    }
}