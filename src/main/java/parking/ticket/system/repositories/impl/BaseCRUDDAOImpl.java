package parking.ticket.system.repositories.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.pts.common.entities.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import parking.ticket.system.constants.MongoConstants;
import parking.ticket.system.exceptions.PersistenceException;

import java.util.Objects;
import java.util.UUID;

import static parking.ticket.system.constants.MongoConstants.MONGO_DATABASE_BEAN_IDENTIFIER;

/**
 * @author anurag.y
 * @since 19/11/18.
 */
@Slf4j
public abstract class BaseCRUDDAOImpl<T> {

    @Autowired
    @Qualifier(MONGO_DATABASE_BEAN_IDENTIFIER)
    protected MongoDatabase mongoDatabase;

    protected String collectionName;


    protected MongoCollection getMongoCollection() {
        return mongoDatabase.getCollection(collectionName, MongoConstants.entityTableMapping.get(collectionName));
    }

    public T addToCollection(T entity) throws PersistenceException {
        log.debug("Got entity:{} to add in Collection : {}", entity, collectionName);
        setEntityId(entity, createEntityId());
        try {
            getMongoCollection().insertOne(entity);
            log.debug("Successfully inserted entity");
        } catch (Exception e) {
            throw new PersistenceException(String.format("Failed to insert entity:%s in Db", entity.toString()));
        }
        return entity;
    }

    public T getFromCollection(final String id) {
        log.info("AbstractBaseCRUDDAOImpl's get called with id : {}", id);

        final Bson query = Filters.eq(getEntityIdFieldIdentifier(), id);
        final T entity = (T) getMongoCollection().find(query).first();
        log.info("For id : {} fetched entity is : {}", id, entity);

        return entity;
    }

    public T updateInCollection(final String id, final T entity) throws PersistenceException {
        log.info("AbstractDAO's update called with id : {}, and entity : {}", id, entity);

        if (StringUtils.isEmpty(getEntityId(entity))) {
            log.warn("Entity: {} id was null thus setting request's path id: {}", entity, id);
            setEntityId(entity, id);
        } else if (getEntityId(entity).compareTo(id) != 0) {
            log.error("Error occurred because request's path id : {} and entity's id : {} is not equal", id,
                    getEntityId(entity));
            throw new PersistenceException("Id in request's path and entity's id are not equal");
        }

        final Bson query = Filters.eq(getEntityIdFieldIdentifier(), id);
        final T returnedResource = (T) getMongoCollection().findOneAndReplace(query,
                entity);

        if (Objects.isNull(returnedResource)) {
            throw new PersistenceException(String.format("Entity not present for given EntityId", id));
        }

        return entity;
    }

    public void deleteFromCollection(final String id) throws PersistenceException {
        log.info("AbstractDAO's delete called with id : {}", id);

        try {
            final Bson query = Filters.eq(getEntityIdFieldIdentifier(), id);
            getMongoCollection().deleteOne(query);
            log.info("Successfully deleted entity where id is : {}", id);
        } catch (final MongoException exception) {
            log.error("Error occurred when trying to delete entity where id : {}, exception", id, exception);
            throw new PersistenceException("Mongo server failed to delete entity for given id", exception);
        }
    }

    protected abstract void setEntityId(final T entity, final String entityId);

    protected abstract String getEntityIdFieldIdentifier();

    protected abstract String getEntityId(final T entity);

    private String createEntityId() {
        final String uuidInStringForm = UUID.randomUUID().toString();

        return uuidInStringForm.replaceAll("-","");
    }
}
