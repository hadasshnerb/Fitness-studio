package gym.management.Sessions;

import gym.management.Instructor;

/**
 * Represents a Ninja session.
 * Inherits common session properties and defines specific details for this session type.
 */
public class NinjaSession extends Session {

    /**
     * Creates a new Ninja session with predefined price and capacity.
     *
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type for the session (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     */
    public NinjaSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.Ninja, dateTimeStr, forum, instructor);
        this.price = 150;
        this.capacity = 5;
    }
}
