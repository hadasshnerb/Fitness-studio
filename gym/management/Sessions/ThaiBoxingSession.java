package gym.management.Sessions;

import gym.management.Instructor;

/**
 * Represents a Thai Boxing session.
 * Inherits common session properties and defines specific details for this session type.
 */
public class ThaiBoxingSession extends Session {

    /**
     * Creates a new Thai Boxing session with predefined price and capacity.
     *
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type for the session (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     */
    public ThaiBoxingSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.ThaiBoxing, dateTimeStr, forum, instructor);
        this.price = 100;
        this.capacity = 20;
    }
}
