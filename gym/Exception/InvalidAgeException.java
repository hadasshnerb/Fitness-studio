package gym.Exception;

/**
 * An exception that is thrown when a person's age is invalid for a specific operation.
 */
public class InvalidAgeException extends Exception {

    /**
     * Creates a new InvalidAgeException with a specific message.
     *
     * @param message the detail message for the exception
     */
    public InvalidAgeException(String message) {
        super(message);
    }
}
