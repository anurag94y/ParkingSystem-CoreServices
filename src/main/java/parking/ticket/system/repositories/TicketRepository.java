package parking.ticket.system.repositories;

import com.pts.common.entities.Ticket;
import org.springframework.stereotype.Repository;
import parking.ticket.system.repositories.impl.BaseCRUDDAOImpl;


/**
 * @author anurag.y
 * @since 12/10/18.
 */
@Repository
public interface TicketRepository extends BaseCRUDDAO<Ticket> {
    Ticket findByTicketNumber(String ticketNumber);
}