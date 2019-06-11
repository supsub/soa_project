package agh.soa;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
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

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try{
            logger.info("\"" + textMessage.getText()+ "\" arrived in the MessagesSystem war:).");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
