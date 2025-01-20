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

    private final MessageProcessorAdapter messageProcessorAdapter;

    public void handleMessage(CommonMessageForm<?> messageForm) {
        MessageProcessor processor = messageProcessorAdapter.getProcessor(messageForm.messageType());
        processor.handleMessage(messageForm);
    }

}
