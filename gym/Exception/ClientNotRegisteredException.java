package gym.Exception;

/**
 * An exception that is thrown when a client is not registered in the gym system.
 */
public class ClientNotRegisteredException extends Exception {

    /**
     * Creates a new ClientNotRegisteredException with a specific message.
     *
     * @param message the detail message for the exception
     */
    public ClientNotRegisteredException(String message) {
        super(message);
    }
}
