package parking.ticket.system.repositories;


import com.pts.common.entities.ParkingSlot;
import com.pts.common.entities.VehicleType;

/**
 * @author anurag.y
 * @since 14/10/18.
 */
public interface ParkingSlotRepository extends BaseCRUDDAO<ParkingSlot> {

    ParkingSlot findOneByBuildingIdAndCompanyIdAndIsReservedAndVehicleTypeAndIsVacant(Long buildingId, Long companyId,
                                                                                      Boolean isReserved,
                                                                                      VehicleType vehicleType, Boolean isVacant);

    ParkingSlot findOneByBuildingIdAndCompanyIdAndVehicleTypeAndIsVacant(Long buildingId, Long companyId,
                                                                         VehicleType vehicleType, Boolean isVacant);

    ParkingSlot findOneByParkingSlotId(String parkingSlotId);
}
