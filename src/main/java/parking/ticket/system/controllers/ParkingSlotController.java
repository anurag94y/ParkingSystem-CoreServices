package parking.ticket.system.controllers;



import com.pts.common.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import parking.ticket.system.managers.ParkingSlotManager;
import parking.ticket.system.pojo.request.ParkingSlotRequest;
import parking.ticket.system.pojo.response.ParkingSlotResponse;



/**
 * @author anurag.y
 * @since 12/10/18.
 */
@RestController
@RequestMapping(value = "/parkingSlot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotManager parkingSlotManager;

    @RequestMapping(method = RequestMethod.POST, value = "/getFreeSlot")
    public ParkingSlotResponse getFreeParkingSlot(@RequestBody ParkingSlotRequest request) {
        ParkingSlotResponse response = null;
        if (request == null) {
           response = ParkingSlotResponse.builder().message("Request Body is Null").status(HttpStatus.BAD_REQUEST).build();
           return response;
        }
        try {
            response = parkingSlotManager.getFreeParkingSlot(request);
        } catch (Exception e) {
            response = ParkingSlotResponse.builder().message("Unable to process request. Exception : " + e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateSlot")
    public ParkingSlotResponse updateParkingSlot(@RequestBody Ticket request) {
        ParkingSlotResponse response = null;
        if (request == null) {
            response = ParkingSlotResponse.builder().message("Request Body is Null").status(HttpStatus.BAD_REQUEST).build();
            return response;
        }
        try {
            response = parkingSlotManager.updateParkingSlot(request);
        } catch (Exception e) {
            response = ParkingSlotResponse.builder().message("Unable to process request. Exception : " + e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }
}
