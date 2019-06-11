package agh.soa;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(
        name = "MessagesSystem",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/SOA_test")
        }
)
public class MessagesSystem implements MessageListener {

    @EJB(lookup = "java:app/message-system-1.0-SNAPSHOT/MessagesService")
    MessagesService messagesService;

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try{
            messagesService.addMessage(((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
