package websocket.example.stompchat.processor;

import websocket.example.stompchat.dto.CommonMessageDto;

public interface CommonChatModule {
    Long increaseSequence(String roomId);
    void saveMessage(CommonMessageDto messageDto);
    void sendMessage(CommonMessageDto messageDto);
    boolean isNewUser(String roomId, String userId);
    void joinRoomUser(String roomId, String userId);
}
