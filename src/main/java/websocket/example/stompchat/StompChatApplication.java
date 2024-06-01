package websocket.example.stompchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "websocket.example.stompchat")
public class StompChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(StompChatApplication.class, args);
    }
}
