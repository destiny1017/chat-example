package websocket.example.stompchat.processor;

import websocket.example.stompchat.dto.CommonMessageForm;

public interface MessageProcessor<T> {

    void handleMessage(CommonMessageForm<T> messageForm);
}
