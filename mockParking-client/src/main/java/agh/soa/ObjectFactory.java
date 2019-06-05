
package agh.soa;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the agh.soa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: agh.soa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParkingPlace }
     * 
     */
    public ParkingPlace createParkingPlace() {
        return new ParkingPlace();
    }

    /**
     * Create an instance of {@link Parkometer }
     * 
     */
    public Parkometer createParkometer() {
        return new Parkometer();
    }

    /**
     * Create an instance of {@link Street }
     * 
     */
    public Street createStreet() {
        return new Street();
    }

    /**
     * Create an instance of {@link Zone }
     * 
     */
    public Zone createZone() {
        return new Zone();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Ticket }
     * 
     */
    public Ticket createTicket() {
        return new Ticket();
    }

}
