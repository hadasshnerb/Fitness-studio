package gym.observer;

/**
 * Represents an entity that can receive and handle messages.
 * Typically used for notifications in the gym system.
 */
public interface Receiver {

    /**
     * Handles an incoming message.
     *
     * @param message the message to process
     */
    void update(String message);
}
