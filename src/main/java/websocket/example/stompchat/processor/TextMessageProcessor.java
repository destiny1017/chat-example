package websocket.example.stompchat.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import websocket.example.stompchat.BlackWordUtil;
import websocket.example.stompchat.dto.CommonMessageDto;
import websocket.example.stompchat.dto.CommonMessageForm;
import websocket.example.stompchat.dto.TextMessageDto;
import websocket.example.stompchat.dto.TextMessageForm;

@Component
@RequiredArgsConstructor
public class TextMessageProcessor implements MessageProcessor<TextMessageForm> {

    private final CommonChatModule chatModule;
    @Override
    public void handleMessage(CommonMessageForm<TextMessageForm> form) {
        // 1. 현재 채팅방의 마지막 sequence 증가
        Long seq = chatModule.increaseSequence(form.roomId());

        // 2. 할당 받은 sequence로 dto 생성
        CommonMessageDto<TextMessageDto> messageDto = createDto(form, seq);

        // 3. 비속어 치환
        String message = messageDto.getMessageDto().getMessage();
        if(BlackWordUtil.isContainBlackWord(message)) {
            message = BlackWordUtil.replaceBlackWords(message);
            messageDto.getMessageDto().setMessage(message);
        }

        // 4. 메세지 저장
        chatModule.saveMessage(messageDto);

        // 5. 메세지 전송
        chatModule.sendMessage(messageDto);

        // 6. 첫 참여자 입장 처리
        if(chatModule.isNewUser(form.roomId(), form.userId())) {
            chatModule.joinRoomUser(form.roomId(), form.userId());
        }
    }

    private CommonMessageDto<TextMessageDto> createDto(CommonMessageForm<TextMessageForm> form, Long seq) {
        TextMessageDto textMessageDto = TextMessageDto.of(form.messageForm());
        textMessageDto.setSequence(seq);
        CommonMessageDto<TextMessageDto> commonMessageDto = CommonMessageDto.of(form);
        commonMessageDto.setMessageDto(textMessageDto);
        return commonMessageDto;
    }
}
