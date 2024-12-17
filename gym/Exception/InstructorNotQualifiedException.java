package gym.Exception;

/**
 * An exception that is thrown when an instructor is not qualified to teach a specific session type.
 */
public class InstructorNotQualifiedException extends Exception {

    /**
     * Creates a new InstructorNotQualifiedException with a specific message.
     *
     * @param message the detail message for the exception
     */
    public InstructorNotQualifiedException(String message) {
        super(message);
    }
}
