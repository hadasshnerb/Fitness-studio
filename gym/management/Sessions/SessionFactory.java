package gym.management.Sessions;

import gym.management.Instructor;

/**
 * A factory class for creating different types of gym sessions.
 */
public class SessionFactory {

    /**
     * Creates a new session based on the provided session type.
     *
     * @param type        the type of session to create (e.g., Pilates, MachinePilates, ThaiBoxing, Ninja)
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type for the session (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     * @return a new Session object of the specified type
     * @throws IllegalArgumentException if the session type is unknown
     */
    public static Session createSession(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) {
        switch (type) {
            case Pilates:
                return new PilatesSession(dateTimeStr, forum, instructor);
            case MachinePilates:
                return new MachinePilatesSession(dateTimeStr, forum, instructor);
            case ThaiBoxing:
                return new ThaiBoxingSession(dateTimeStr, forum, instructor);
            case Ninja:
                return new NinjaSession(dateTimeStr, forum, instructor);
            default:
                throw new IllegalArgumentException("Unknown session type: " + type);
        }
    }
}
