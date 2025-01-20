package websocket.example.stompchat;

import lombok.RequiredArgsConstructor;
import websocket.example.stompchat.constants.MessageType;
import websocket.example.stompchat.processor.ImageMessageProcessor;
import websocket.example.stompchat.processor.MessageProcessor;
import websocket.example.stompchat.processor.TextMessageProcessor;

@RequiredArgsConstructor
public class MessageProcessorAdapter {

    private final TextMessageProcessor textMessageProcessor;
    private final ImageMessageProcessor imageMessageProcessor;

    public MessageProcessor<?> getProcessor(MessageType messageType) {
        return switch (messageType) {
            case TEXT -> textMessageProcessor;
            case IMAGE -> imageMessageProcessor;
        };
    }
}
