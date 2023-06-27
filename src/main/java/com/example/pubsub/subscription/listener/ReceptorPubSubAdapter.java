package com.example.pubsub.subscription.listener;

import com.example.pubsub.subscription.domain.PubSubMessageResponse;
import com.example.pubsub.subscription.domain.PubSubMessageSub;
import com.example.pubsub.subscription.repository.SubscriberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.cloud.spring.pubsub.support.AcknowledgeablePubsubMessage;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Repository
public class ReceptorPubSubAdapter implements SubscriberRepository {

    @Value("${pubsub.subscription.leitorExampleSub}")
    String subscription;

    @Autowired
    private PubSubSubscriberTemplate pubSubSubscriberTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PubSubMessageResponse receiveMessage() {
//        Subscription.newBuilder().setEnableMessageOrdering(true).build();

        PubSubMessageResponse pubSubMessageResponse = new PubSubMessageResponse();
        List<AcknowledgeablePubsubMessage> pubsubMessages = pubSubSubscriberTemplate.pull(subscription, null, true);

        for (AcknowledgeablePubsubMessage pubsubMessage: pubsubMessages) {
            PubsubMessage pubsubMessageSub = pubsubMessage.getPubsubMessage();

            String id = pubsubMessageSub.getMessageId();
            log.info("..: ReceptorPubSubAdapter message id: {}", id);

            try {
                PubSubMessageSub pubSubMessageSub = objectMapper.readValue(pubsubMessageSub.getData().toString(StandardCharsets.UTF_8), PubSubMessageSub.class);
                log.info("..: ReceptorPubSubAdapter mensagem recebida: {}", pubSubMessageSub);
                pubSubMessageResponse.getMessages().add(pubSubMessageSub.getMessage());
            } catch (JsonProcessingException e) {
                log.error("..: erro de conversao da mensagem", e);
            }

        }

        if (!pubsubMessages.isEmpty())
            pubSubSubscriberTemplate.ack(pubsubMessages);

        return pubSubMessageResponse;
    }
}
