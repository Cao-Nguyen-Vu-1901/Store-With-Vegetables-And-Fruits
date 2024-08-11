package com.cuahangnongsan.notificated;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Xử lý thông điệp từ client
        System.out.println("Received message: " + message.getPayload());

        // Gửi thông điệp phản hồi đến client
        session.sendMessage(new TextMessage("Hello, client!"));
    }
}

