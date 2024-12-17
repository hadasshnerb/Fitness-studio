package gym.management.Sessions;

import gym.management.Instructor;

/**
 * Represents a standard Pilates session.
 * Inherits common session properties and defines specific details for this session type.
 */
public class PilatesSession extends Session {

    /**
     * Creates a new Pilates session with predefined price and capacity.
     *
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type for the session (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     */
    public PilatesSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.Pilates, dateTimeStr, forum, instructor);
        this.price = 60;
        this.capacity = 30;
    }
}
