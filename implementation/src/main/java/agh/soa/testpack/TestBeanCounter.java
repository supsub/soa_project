package agh.soa.testpack;

import lombok.Getter;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Singleton;

@Getter
@Singleton
@PermitAll
@SecurityDomain("mysqldomain")
@Remote(ITestBeanCounter.class)
public class TestBeanCounter implements ITestBeanCounter {

    private long number = 0;

    @Override
    public void increment() {
        number++;
    }

    @Override
    @RolesAllowed("Manager")
    public void decrement() {
        number--;
    }


}
