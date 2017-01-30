package uk.co.luciditysoftware.actsintown.api.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import uk.co.luciditysoftware.actsintown.api.utilities.Mezzage;
import uk.co.luciditysoftware.actsintown.api.utilities.OutputMezzage;

public class WebSocketTestController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMezzage send(Mezzage message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMezzage(message.getFrom(), message.getText(), time);
    }
}
