package agh.soa;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MessagesService {

    private List<String> messages = new ArrayList<>();

    public void addMessage(String message){
        System.out.println(message);
        if(message.startsWith("<<PARKING PLACE FREED>>") || message.startsWith("<<TICKET BOUGHT>>")){
            String id = message.replace("[^0-9]+", "");
            System.out.println("id in service " + id);
            this.setMessages(messages.stream()
                    .filter(msg -> msg.contains(id))
                    .collect(Collectors.toList()));
        }else {
            this.messages.add(message);
        }
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
