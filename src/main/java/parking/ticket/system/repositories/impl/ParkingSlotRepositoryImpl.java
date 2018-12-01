package parking.ticket.system.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.pts.common.entities.ParkingSlot;
import com.pts.common.entities.VehicleType;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import parking.ticket.system.constants.MongoConstants;
import parking.ticket.system.repositories.BaseCRUDDAO;
import parking.ticket.system.repositories.ParkingSlotRepository;

/**
 * @author anurag.y
 * @since 20/11/18.
 */
@Repository
public class ParkingSlotRepositoryImpl extends BaseCRUDDAOImpl<ParkingSlot> implements ParkingSlotRepository {


    public ParkingSlotRepositoryImpl() {
        collectionName = MongoConstants.PARKING_SLOT_COLLECTION_NAME;
    }

    @Override
    public ParkingSlot findOneByBuildingIdAndCompanyIdAndIsReservedAndVehicleTypeAndIsVacant(Long buildingId, Long companyId, Boolean isReserved, VehicleType vehicleType, Boolean isVacant) {
        final MongoCollection mongoCollection = getMongoCollection();
        final Bson searchQuery = Filters.and(Filters.eq(MongoConstants.BUILDING_ID, buildingId.toString()),
                Filters.eq(MongoConstants.COMPANY_ID, companyId.toString()),
                Filters.eq(MongoConstants.IS_RESERVED, isReserved),
                Filters.eq(MongoConstants.VEHICLE_TYPE, vehicleType.toString()),
                Filters.eq(MongoConstants.IS_VACANT, isVacant));
        return (ParkingSlot) mongoCollection.find(searchQuery).first();
    }

    @Override
    public ParkingSlot findOneByBuildingIdAndCompanyIdAndVehicleTypeAndIsVacant(Long buildingId, Long companyId, VehicleType vehicleType, Boolean isVacant) {
        final MongoCollection mongoCollection = getMongoCollection();
        final Bson searchQuery = Filters.and(Filters.eq(MongoConstants.BUILDING_ID, buildingId),
                Filters.eq(MongoConstants.COMPANY_ID, companyId),
                Filters.eq(MongoConstants.VEHICLE_TYPE, vehicleType.toString()),
                Filters.eq(MongoConstants.IS_VACANT, isVacant));
        return (ParkingSlot) mongoCollection.find(searchQuery).first();
    }

    @Override
    public ParkingSlot findOneByParkingSlotId(String parkingSlotId) {
        final MongoCollection mongoCollection = getMongoCollection();
        final Bson searchQuery = Filters.eq(MongoConstants.PARKING_SLOT_ID, parkingSlotId);
        return (ParkingSlot) mongoCollection.find(searchQuery).first();
    }

    @Override
    protected void setEntityId(ParkingSlot entity, String entityId) {
        entity.setParkingSlotId(entityId);
    }

    @Override
    protected String getEntityIdFieldIdentifier() {
        return MongoConstants.PARKING_SLOT_ID;
    }

    @Override
    protected String getEntityId(ParkingSlot entity) {
        return entity.getParkingSlotId();
    }
}
