package parking.ticket.system.managers;

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.pts.common.entities.ParkingSlot;
import com.pts.common.entities.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import parking.ticket.system.client.ParkingSlotCleint;
import parking.ticket.system.exceptions.PersistenceException;
import parking.ticket.system.repositories.ParkingSlotRepository;
import parking.ticket.system.repositories.TicketRepository;
import parking.ticket.system.pojo.request.TicketRequest;
import parking.ticket.system.pojo.response.TicketResponse;


import java.util.UUID;

/**
 * @author anurag.y
 * @since 13/10/18.
 */
@Slf4j
@Component
public class TicketManager {

    @Autowired
    private ParkingSlotCleint parkingSlotCleint;

    @Autowired
    private TicketRepository ticketRepository;

    private final Gson gson = new Gson();

    public TicketResponse createTicket(TicketRequest request) {
        ParkingSlot parkingSlot = parkingSlotCleint.getFreeParkingSlot(request);

        if (parkingSlot == null) {
            return TicketResponse.builder().message("No free slot is available for parking").status(HttpStatus.ACCEPTED).build();
        }

        Ticket ticket = Ticket.builder()
                .VehicleNumber(request.getVehicleNumber())
                .parkingSlotId(parkingSlot.getParkingSlotId())
                .ticketNumber(generateTicketId())
                .build();
        try {
            ticketRepository.addToCollection(ticket);
            parkingSlotCleint.updateParkingSlot(parkingSlot);
        } catch (PersistenceException e) {
            return TicketResponse.builder().message("Failed to process Request : " +  gson.toJson(request)).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        TicketResponse ticketResponse = TicketResponse.builder().message(gson.toJson(ticket)).status(HttpStatus.ACCEPTED).build();
        return ticketResponse;
    }

    public TicketResponse updateTicket(Ticket request) throws Exception {
        TicketResponse ticketResponse = TicketResponse.builder().build();
        Ticket originalTicket = ticketRepository.findByTicketNumber(request.getTicketNumber());
        if (originalTicket == null) {
            return TicketResponse.builder().message("No ticket present for this ticket number").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setParkingSlotId(request.getParkingSlotId());
        ParkingSlot parkingSlotResponse = parkingSlotCleint.updateParkingSlot(parkingSlot);
        if (parkingSlotResponse == null) {
            return TicketResponse.builder().message("No slot is attched to this ticket").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ticketResponse.setMessage("Slot Has been updated");
        ticketResponse.setStatus(HttpStatus.ACCEPTED);
        return ticketResponse;
    }

    private String generateTicketId() {
        UUID uniqueid = Generators.timeBasedGenerator().generate();
        return "T-" + uniqueid.toString().replaceAll("-", "");
    }
}