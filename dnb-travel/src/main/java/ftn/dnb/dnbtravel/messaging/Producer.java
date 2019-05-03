package ftn.dnb.dnbtravel.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendMessageTo(String friendsUsername, String myUsername) {
        this.template.convertAndSend("/topic/" + friendsUsername, myUsername);
    }
}
