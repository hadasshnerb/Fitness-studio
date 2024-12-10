package gym.management.Sessions;

import gym.management.Instructor;

public class MachinePilatesSession extends Session {
    public MachinePilatesSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.MachinePilates, dateTimeStr, forum, instructor);
        this.price = 80;
        this.capacity = 10;
    }
}
