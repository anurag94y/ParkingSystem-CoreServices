package parking.ticket.system.pojo.request;


import com.pts.common.entities.VehicleType;
import lombok.Builder;
import lombok.Data;

/**
 * @author anurag.y
 * @since 14/10/18.
 */
@Data
@Builder
public class TicketRequest {
    private String vehicleNumber;
    private Long buildingId;
    private Long companyId;
    private VehicleType vehicleType;
    private Boolean isReserved;
}
