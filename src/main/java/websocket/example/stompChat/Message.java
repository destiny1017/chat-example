package websocket.example.stompChat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String type;
    private String sender;
    private String channelId;
    private Object data;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void newConnection() {
        this.type = "new";
    }

    public void closeConnection() {
        this.type = "close";
    }
}
