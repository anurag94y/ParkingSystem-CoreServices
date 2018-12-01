package parking.ticket.system.managers;

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.pts.common.entities.ParkingSlot;
import com.pts.common.entities.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import parking.ticket.system.exceptions.PersistenceException;
import parking.ticket.system.repositories.ParkingSlotRepository;
import parking.ticket.system.repositories.TicketRepository;
import parking.ticket.system.pojo.request.ParkingSlotRequest;
import parking.ticket.system.pojo.response.ParkingSlotResponse;


import java.util.UUID;

/**
 * @author anurag.y
 * @since 13/10/18.
 */
@Slf4j
@Component
public class ParkingSlotManager {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private final Gson gson = new Gson();

    public ParkingSlotResponse getFreeParkingSlot(ParkingSlotRequest request) {
        ParkingSlotResponse parkingSlotResponse = ParkingSlotResponse.builder().build();
        ParkingSlot parkingSlot;
        if (request.getIsReserved()) {
             parkingSlot = parkingSlotRepository.findOneByBuildingIdAndCompanyIdAndVehicleTypeAndIsVacant(
                            request.getBuildingId(), request.getCompanyId(), request.getVehicleType(), true);
        } else {
            parkingSlot = parkingSlotRepository.findOneByBuildingIdAndCompanyIdAndIsReservedAndVehicleTypeAndIsVacant(
                    request.getBuildingId(), request.getCompanyId(), request.getIsReserved(),
                    request.getVehicleType(), true);
        }

        if (parkingSlot == null) {
            return ParkingSlotResponse.builder().message("No free slot is available for parking").status(HttpStatus.ACCEPTED).build();
        }

        Ticket ticket = Ticket.builder()
                .VehicleNumber(request.getVehicleNumber())
                .parkingSlotId(parkingSlot.getParkingSlotId())
                .ticketNumber(generateTicketId())
                .build();
        try {
            ticketRepository.addToCollection(ticket);
            parkingSlot.setIsVacant(false);
            parkingSlotRepository.updateInCollection(parkingSlot.getParkingSlotId(), parkingSlot);
        } catch (PersistenceException e) {
            return ParkingSlotResponse.builder().message("Failed to process Request : " +  gson.toJson(request)).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        parkingSlotResponse.setMessage(gson.toJson(ticket));
        parkingSlotResponse.setStatus(HttpStatus.ACCEPTED);
        return parkingSlotResponse;
    }

    public ParkingSlotResponse updateParkingSlot(Ticket request) throws Exception {
        ParkingSlotResponse parkingSlotResponse = ParkingSlotResponse.builder().build();
        Ticket originalTicket = ticketRepository.findByTicketNumber(request.getTicketNumber());
        if (originalTicket == null) {
            return ParkingSlotResponse.builder().message("No ticket present for this ticket number").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ParkingSlot parkingSlot = parkingSlotRepository.findOneByParkingSlotId(originalTicket.getParkingSlotId());
        if (parkingSlot == null) {
            return ParkingSlotResponse.builder().message("No slot is attched to this ticket").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        parkingSlot.setIsVacant(true);
        parkingSlotRepository.updateInCollection(parkingSlot.getParkingSlotId(), parkingSlot);
        parkingSlotResponse.setMessage("Slot Has been updated");
        parkingSlotResponse.setStatus(HttpStatus.ACCEPTED);
        return parkingSlotResponse;
    }

    private String generateTicketId() {
        UUID uniqueid = Generators.timeBasedGenerator().generate();
        return "T-" + uniqueid.toString().replaceAll("-", "");
    }
}