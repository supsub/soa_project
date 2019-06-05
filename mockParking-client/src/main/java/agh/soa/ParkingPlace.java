
package agh.soa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parkingPlace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parkingPlace"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ordinalNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="parkometer" type="{http://soap.soa.agh/}parkometer" minOccurs="0"/&gt;
 *         &lt;element name="taken" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ticket" type="{http://soap.soa.agh/}ticket" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parkingPlace", propOrder = {
    "id",
    "ordinalNumber",
    "parkometer",
    "taken",
    "ticket"
})
public class ParkingPlace {

    protected int id;
    protected int ordinalNumber;
    protected Parkometer parkometer;
    protected boolean taken;
    protected Ticket ticket;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the ordinalNumber property.
     * 
     */
    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    /**
     * Sets the value of the ordinalNumber property.
     * 
     */
    public void setOrdinalNumber(int value) {
        this.ordinalNumber = value;
    }

    /**
     * Gets the value of the parkometer property.
     * 
     * @return
     *     possible object is
     *     {@link Parkometer }
     *     
     */
    public Parkometer getParkometer() {
        return parkometer;
    }

    /**
     * Sets the value of the parkometer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Parkometer }
     *     
     */
    public void setParkometer(Parkometer value) {
        this.parkometer = value;
    }

    /**
     * Gets the value of the taken property.
     * 
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     * Sets the value of the taken property.
     * 
     */
    public void setTaken(boolean value) {
        this.taken = value;
    }

    /**
     * Gets the value of the ticket property.
     * 
     * @return
     *     possible object is
     *     {@link Ticket }
     *     
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets the value of the ticket property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ticket }
     *     
     */
    public void setTicket(Ticket value) {
        this.ticket = value;
    }

}
