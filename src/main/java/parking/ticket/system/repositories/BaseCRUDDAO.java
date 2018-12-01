package parking.ticket.system.repositories;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.util.StringUtils;
import parking.ticket.system.exceptions.PersistenceException;

import java.util.Objects;

/**
 * @author anurag.y
 * @since 20/11/18.
 */
public interface BaseCRUDDAO<T> {
    public T addToCollection(T entity) throws PersistenceException;

    public T getFromCollection(final String id);

    public T updateInCollection(final String id, final T entity) throws PersistenceException;

    public void deleteFromCollection(final String id) throws PersistenceException;
}
