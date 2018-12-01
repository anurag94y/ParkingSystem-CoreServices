package parking.ticket.system.exceptions;

/**
 * @author anurag.y
 * @since 30/11/18.
 */
public class PersistenceException extends Exception {
    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Exception e) {
        super(message, e);
    }
}
