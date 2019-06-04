package agh.soa;

import agh.soa.model.ParkingPlace;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IParkingService {

    @WebMethod
    boolean isEmpty(@WebParam(name = "parkingPlace") ParkingPlace place);

    @WebMethod
    boolean takePlace(@WebParam(name = "parkingPlace") ParkingPlace place);

    @WebMethod
    boolean freePlace(@WebParam(name = "parkingPlace") ParkingPlace place);
}
