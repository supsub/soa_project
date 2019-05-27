package agh.soa.testpack;

import lombok.Data;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "testView")
@Data
public class TestView implements Serializable {


    @EJB(lookup = "java:global/implementation-1.0-SNAPSHOT/TestBeanCounter")
    ITestBeanCounter counter;


    public void increment(){
        counter.increment();
    }

    public void decrement(){
        counter.decrement();
    }


}
