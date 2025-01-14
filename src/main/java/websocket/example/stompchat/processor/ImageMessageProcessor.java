package websocket.example.stompchat.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import websocket.example.stompchat.dto.CommonMessageForm;
import websocket.example.stompchat.dto.ImageMessageForm;

@Component
@RequiredArgsConstructor
public class ImageMessageProcessor implements MessageProcessor<ImageMessageForm> {

    private final CommonChatModule chatModule;

    @Override
    public void handleMessage(CommonMessageForm<ImageMessageForm> messageForm) {

    }
}
