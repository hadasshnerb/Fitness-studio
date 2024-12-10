package gym.management.Sessions;

import gym.management.Instructor;

public class ThaiBoxingSession extends Session {
    public ThaiBoxingSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.ThaiBoxing, dateTimeStr, forum, instructor);
        this.price = 100;
        this.capacity = 20;
    }
}
