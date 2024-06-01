package websocket.example.stompchat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

//    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final RabbitTemplate rabbitTemplate;
    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";


    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(ChatMessage chat, @DestinationVariable String chatRoomId) {
        log.info("enter room Id = {}, chat = {}", chatRoomId, chat.toString());
        chat.setMessage(chat.getMemberId() + "님이 입장하셨습니다.");
        chat.setRegDate(LocalDateTime.now());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "enter.room." + chatRoomId, chat);
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void send(ChatMessage chat, @DestinationVariable String chatRoomId){
        log.info("message room Id = {}, chat = {}", chatRoomId, chat.toString());
        chat.setRegDate(LocalDateTime.now());

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "*.room." + chatRoomId, chat);
    }

}
