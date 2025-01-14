package websocket.example.stompchat.dto;

public record ImageMessageForm(
        String imageUrl,
        String thumbnailUrl
) {
}
