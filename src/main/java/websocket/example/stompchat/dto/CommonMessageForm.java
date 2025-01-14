package websocket.example.stompchat.dto;

import websocket.example.stompchat.constants.MessageType;

public record CommonMessageForm<T>(
        MessageType messageType,
        String roomId,
        String userId,
        T messageForm
) {
}