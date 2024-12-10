package gym.management.Sessions;

import gym.management.Instructor;

public class PilatesSession extends Session {
    public PilatesSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.Pilates, dateTimeStr, forum, instructor);
        this.price = 60;
        this.capacity = 30;
    }
}
