package parking.ticket.system.client;

import com.pts.common.entities.ParkingSlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import parking.ticket.system.pojo.request.TicketRequest;

/**
 * @author anurag.y
 * @since 02/01/19.
 */
@Configuration
@ConfigurationProperties(prefix = "parkingSlot")
public class ParkingSlotCleint {

    private String baseurl;
    private String getParkingSlot;
    private String updateParkingSlot;

    public ParkingSlot getFreeParkingSlot(TicketRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseurl + getParkingSlot;
        ResponseEntity<ParkingSlot> responseEntity = restTemplate.postForEntity(url, request, ParkingSlot.class);
        return responseEntity.getBody();
    }

    public ParkingSlot updateParkingSlot(ParkingSlot request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseurl + updateParkingSlot;
        ResponseEntity<ParkingSlot> responseEntity = restTemplate.postForEntity(url, request, ParkingSlot.class);
        return responseEntity.getBody();
    }
}
