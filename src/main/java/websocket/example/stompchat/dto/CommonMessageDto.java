package websocket.example.stompchat.dto;

import lombok.Builder;
import lombok.Data;
import websocket.example.stompchat.constants.MessageType;

@Data
@Builder
public class CommonMessageDto<T> {
    private MessageType messageType;
    private String roomId;
    private String userId;
    private T messageDto;

    public static CommonMessageDto of(CommonMessageForm<?> form) {
        return CommonMessageDto.builder()
                .messageType(form.messageType())
                .roomId(form.roomId())
                .userId(form.userId())
                .build();
    }
}
