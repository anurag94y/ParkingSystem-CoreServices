package parking.ticket.system.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.pts.common.entities.ParkingSlot;
import com.pts.common.entities.Ticket;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import parking.ticket.system.constants.MongoConstants;
import parking.ticket.system.repositories.TicketRepository;


/**
 * @author anurag.y
 * @since 12/10/18.
 */
@Repository
public class TicketRepositoryImpl extends BaseCRUDDAOImpl<Ticket> implements TicketRepository {


    public TicketRepositoryImpl() {
        collectionName = MongoConstants.TICKET_COLLECTION_NAME;
    }

    public Ticket findByTicketNumber(String ticketNumber) {
        final MongoCollection mongoCollection = getMongoCollection();
        final Bson searchQuery = Filters.eq(MongoConstants.TICKET_NUMBER, ticketNumber);
        return (Ticket) mongoCollection.find(searchQuery).first();
    }

    @Override
    protected void setEntityId(Ticket entity, String entityId) {
        entity.setTicketId(entityId);
    }

    @Override
    protected String getEntityIdFieldIdentifier() {
        return MongoConstants.TICKET_ID;
    }

    @Override
    protected String getEntityId(Ticket entity) {
        return entity != null? entity.getTicketId() : null;
    }
}
