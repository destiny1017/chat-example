package websocket.example.stompchat.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    TEXT("텍스트"),
    IMAGE("이미지");

    private final String description;

}
