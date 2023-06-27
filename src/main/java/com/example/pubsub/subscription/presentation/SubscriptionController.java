package com.example.pubsub.subscription.presentation;

import com.example.pubsub.subscription.domain.PubSubMessageResponse;
import com.example.pubsub.subscription.service.PubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    private PubSubService pubSubService;

    @GetMapping("/pubsub")
    public ResponseEntity<List<String>> receiveMessage() {
        PubSubMessageResponse pubSubMessageResponse = pubSubService.receiveMessage();
        if(pubSubMessageResponse.getMessages().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(pubSubMessageResponse.getMessages());
    }
}
