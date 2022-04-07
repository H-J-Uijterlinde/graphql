package nl.quintor.pokedex.controllers;

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
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private final AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GraphQL graphQL;

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        log.info("Server connection opened");
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        log.info("Server connection closed: {}", status);
    }

    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        SubscriptionRequest subscriptionRequest = objectMapper.readValue(message.getPayload(), SubscriptionRequest.class);

        if (!subscriptionRequest.getType().equals("start")) {
            return;
        }

        SubscriptionRequest.Payload payload = subscriptionRequest.getPayload();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(payload.getQuery())
                .operationName(payload.getOperationName())
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);
        Publisher<ExecutionResult> publisher = executionResult.getData();

        publisher.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                subscriptionRef.set(s);
                request();
            }

            @Override
            public void onNext(ExecutionResult executionResult) {
                log.info("On next called on publisher");
                try {
                    Object update = executionResult.getData();
                    SubscriptionResponse response = new SubscriptionResponse();
                    response.setId(subscriptionRequest.getId());
                    response.setPayload(update);
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                request();
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
    public void handleTransportError(@NotNull WebSocketSession session, Throwable exception) {
        log.info("Server transport error: {}", exception.getMessage());
    }

    @NotNull
    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("graphql-ws");
    }

    private void request() {
        Subscription subscription = subscriptionRef.get();
        if (subscription != null) {
            subscription.request(1);
        }
    }
}
