package parking.ticket.system.constants;

import com.google.common.collect.ImmutableMap;
import com.pts.common.entities.ParkingSlot;
import com.pts.common.entities.Ticket;

import java.util.Map;

/**
 * @author anurag.y
 * @since 19/11/18.
 */
public class MongoConstants {
    public final static String MONGO_CLIENT_BEAN_IDENTIFIER = "mongoClientBean";
    public final static String MONGO_DATABASE_BEAN_IDENTIFIER = "mongoDatabaseBean";
    public final static String MONGO_CODEC_REGISTRY_BEAN_IDENTIFIER = "mongoCodecRegistry";
    public final static String MONGO_PROPERTIES_FILE_SOURCE = "classpath:mongodb.properties";
    public final static String TICKET_COLLECTION_NAME = "ticket";
    public final static String PARKING_SLOT_COLLECTION_NAME = "parkingSlot";

    public final static Map<String, Class> entityTableMapping = ImmutableMap.of(
            TICKET_COLLECTION_NAME, Ticket.class,
            PARKING_SLOT_COLLECTION_NAME, ParkingSlot.class);

    public static final String PARKING_SLOT_ID = "parkingSlotId";
    public static final String BUILDING_ID = "buildingId";
    public static final String COMPANY_ID = "companyId";
    public static final String VEHICLE_TYPE = "vehicleType";
    public static final String IS_RESERVED = "isReserved";
    public static final String IS_VACANT = "isVacant";
    public static final String FLOOR_NUMBER = "floorNumber";
    public static final String SLOT_NUMBER = "slotNumber";


    public static final String TICKET_ID = "ticketId";
    public static final String TICKET_NUMBER = "ticketNumber";
}
