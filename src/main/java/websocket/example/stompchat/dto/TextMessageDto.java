package websocket.example.stompchat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TextMessageDto {
    private Long sequence;
    private String message;

    public static TextMessageDto of(TextMessageForm form) {
        return TextMessageDto.builder()
                .message(form.message())
                .build();
    }

}
