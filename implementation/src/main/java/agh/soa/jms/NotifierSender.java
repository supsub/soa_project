package agh.soa.jms;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
@Remote(INotifierSender.class)
public class NotifierSender implements INotifierSender {

    @Resource(mappedName = "java:jboss/exported/jms/queue/SOA_test")
    private Queue queueTest;

    @Inject
    JMSContext context;

    @Override
    public void sendMessage(String txt) {
        try {
            context.createProducer().send(queueTest, txt);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}