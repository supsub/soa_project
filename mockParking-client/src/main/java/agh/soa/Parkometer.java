
package agh.soa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parkometer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parkometer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ordinalNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="parkingPlaces" type="{http://soap.soa.agh/}parkingPlace" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="street" type="{http://soap.soa.agh/}street" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parkometer", propOrder = {
    "id",
    "ordinalNumber",
    "parkingPlaces",
    "street"
})
public class Parkometer {

    protected int id;
    protected int ordinalNumber;
    @XmlElement(nillable = true)
    protected List<ParkingPlace> parkingPlaces;
    protected Street street;

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
     * Gets the value of the parkingPlaces property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parkingPlaces property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParkingPlaces().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParkingPlace }
     * 
     * 
     */
    public List<ParkingPlace> getParkingPlaces() {
        if (parkingPlaces == null) {
            parkingPlaces = new ArrayList<ParkingPlace>();
        }
        return this.parkingPlaces;
    }

    /**
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link Street }
     *     
     */
    public Street getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link Street }
     *     
     */
    public void setStreet(Street value) {
        this.street = value;
    }

}
