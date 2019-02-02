package parking.ticket.system.controllers;



import com.pts.common.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import parking.ticket.system.managers.TicketManager;
import parking.ticket.system.pojo.request.TicketRequest;
import parking.ticket.system.pojo.response.TicketResponse;



/**
 * @author anurag.y
 * @since 12/10/18.
 */
@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketManager ticketManager;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public TicketResponse getFreeParkingSlot(@RequestBody TicketRequest request) {
        TicketResponse response = null;
        if (request == null) {
           response = TicketResponse.builder().message("Request Body is Null").status(HttpStatus.BAD_REQUEST).build();
           return response;
        }
        try {
            response = ticketManager.createTicket(request);
        } catch (Exception e) {
            response = TicketResponse.builder().message("Unable to process request. Exception : " + e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public TicketResponse updateParkingSlot(@RequestBody Ticket request) {
        TicketResponse response = null;
        if (request == null) {
            response = TicketResponse.builder().message("Request Body is Null").status(HttpStatus.BAD_REQUEST).build();
            return response;
        }
        try {
            response = ticketManager.updateTicket(request);
        } catch (Exception e) {
            response = TicketResponse.builder().message("Unable to process request. Exception : " + e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }
}
