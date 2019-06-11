package agh.soa;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class ViewBean {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @EJB(lookup = "java:app/message-system-1.0-SNAPSHOT/MessagesService")
    MessagesService messagesService;

    public List<String> getMessages() {
        return messagesService.getMessages();
    }

    public void polling(){}
}
