package agh.soa.soapController;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IParkingService {

    @WebMethod
    boolean takePlace(@WebParam(name = "ordinalNumber") int ordinalNumber, @WebParam(name = "parkometerID") int parkometerID);

    @WebMethod
    boolean freePlace(@WebParam(name = "ordinalNumber") int ordinalNumber, @WebParam(name = "parkometerID") int parkometerID);
}
