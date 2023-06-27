package com.example.pubsub.subscription.service;

import com.example.pubsub.subscription.domain.PubSubMessageResponse;
import com.example.pubsub.subscription.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public PubSubMessageResponse receiveMessage() {

        return subscriberRepository.receiveMessage();
    }
}
