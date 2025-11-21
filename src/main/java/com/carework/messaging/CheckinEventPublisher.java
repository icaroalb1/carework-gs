package com.carework.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckinEventPublisher {

    private final MessageChannel checkinEventsChannel;

    public void publish(CheckinEvent event) {
        checkinEventsChannel.send(MessageBuilder.withPayload(event).build());
    }
}
