package com.example.pubsub.subscription.repository;

import com.example.pubsub.subscription.domain.PubSubMessageResponse;

public interface SubscriberRepository {

    PubSubMessageResponse receiveMessage();

}
