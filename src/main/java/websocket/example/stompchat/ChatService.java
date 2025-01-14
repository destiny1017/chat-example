package websocket.example.stompchat;

import lombok.RequiredArgsConstructor;
import websocket.example.stompchat.constants.MessageType;
import websocket.example.stompchat.dto.CommonMessageForm;
import websocket.example.stompchat.dto.TextMessageForm;
import websocket.example.stompchat.processor.ImageMessageProcessor;
import websocket.example.stompchat.processor.MessageProcessor;
import websocket.example.stompchat.processor.TextMessageProcessor;

@RequiredArgsConstructor
public class ChatService {

    private final TextMessageProcessor textMessageProcessor;
    private final ImageMessageProcessor imageMessageProcessor;

    public void handleMessage(CommonMessageForm<?> messageForm) {
        MessageProcessor processor = getProcessor(messageForm.messageType());
        processor.handleMessage(messageForm);
    }

    private MessageProcessor<?> getProcessor(MessageType messageType) {
        return switch (messageType) {
            case TEXT -> textMessageProcessor;
            case IMAGE -> imageMessageProcessor;
        };
    }
}
