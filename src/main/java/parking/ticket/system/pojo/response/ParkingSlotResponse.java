package parking.ticket.system.pojo.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author anurag.y
 * @since 12/10/18.
 */
@Builder
@Data
public class ParkingSlotResponse {
    private HttpStatus status;
    private String message;
}
