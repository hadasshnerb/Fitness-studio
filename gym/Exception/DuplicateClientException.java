package gym.Exception;

/**
 * An exception that is thrown when a duplicate client is added to the gym system.
 */
public class DuplicateClientException extends Exception {

    /**
     * Creates a new DuplicateClientException with a specific message.
     *
     * @param message the detail message for the exception
     */
    public DuplicateClientException(String message) {
        super(message);
    }
}
