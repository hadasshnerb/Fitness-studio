package gym.management.Sessions;

import gym.management.Instructor;

/**
 * Represents a machine-based Pilates session.
 * Inherits common session properties and defines specific details for this session type.
 */
public class MachinePilatesSession extends Session {

    /**
     * Creates a new Machine Pilates session with predefined price and capacity.
     *
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type for the session (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     */
    public MachinePilatesSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.MachinePilates, dateTimeStr, forum, instructor);
        this.price = 80;
        this.capacity = 10;
    }
}
