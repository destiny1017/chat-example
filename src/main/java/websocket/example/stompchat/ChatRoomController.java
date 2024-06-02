package websocket.example.stompchat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatRoomController {

    @GetMapping("/chat-room/{roomId}/{nickname}")
    public String chatRoom(@PathVariable Long roomId, @PathVariable String nickname, Model model) {
        model.addAttribute("chatRoomId", roomId);
        model.addAttribute("nickname", nickname);
        return "chat-room";
    }

}
