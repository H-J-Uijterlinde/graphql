package nl.quintor.pokedex.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
@Component
public class SubscriptionHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private final AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GraphQL graphQL;

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        log.info("Server connection opened");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Server connection closed: {}", status);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();
        log.info("Server received: {}", request);

        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
        Map<String, Object> requestAsMap = objectMapper.readValue(request, typeReference);

        if (!requestAsMap.get("type").equals("start")) {
            return;
        }

        String requestId = (String) requestAsMap.get("id");
        Map<String, String> payloadAsMap = (Map<String, String>) requestAsMap.get("payload");

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(payloadAsMap.get("query"))
                .operationName(payloadAsMap.get("operationName"))
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);
        Publisher<ExecutionResult> publisher = executionResult.getData();

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                subscriptionRef.set(s);
                request(1);
            }

            @Override
            public void onNext(ExecutionResult executionResult) {
                log.info("On next called on publisher");
                try {
                    Object update = executionResult.getData();
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of("payload", update, "id", requestId, "type", "data"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                request(1);
            }

            @Override
            public void onError(Throwable t) {
                log.error("Subscription threw an exception", t);
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                log.info("Subscription complete");
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.info("Server transport error: {}", exception.getMessage());
    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("graphql-ws");
    }

    private void request(int n) {
        Subscription subscription = subscriptionRef.get();
        if (subscription != null) {
            subscription.request(n);
        }
    }
}
