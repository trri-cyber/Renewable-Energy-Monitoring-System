package org.example.apigateway.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@Order(-1)
public class GlobalExceptionFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return chain.filter(exchange)
                .onErrorResume(ex -> {

                    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

                    ErrorResponse response = new ErrorResponse(
                            status.value(),
                            status.getReasonPhrase(),
                            ex.getMessage(),
                            exchange.getRequest().getPath().value(),
                            LocalDateTime.now()
                    );

                    String json = """
                            {
                              "status": %d,
                              "error": "%s",
                              "message": "%s",
                              "path": "%s",
                              "timestamp": "%s"
                            }
                            """.formatted(
                            response.getStatus(),
                            response.getError(),
                            response.getMessage(),
                            response.getPath(),
                            response.getTimestamp()
                    );

                    exchange.getResponse().setStatusCode(status);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                    return exchange.getResponse().writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(json.getBytes(StandardCharsets.UTF_8))
                            )
                    );
                });
    }
}