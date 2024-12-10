package gym.management.Sessions;

import gym.management.Instructor;

public class NinjaSession extends Session {
    public NinjaSession(String dateTimeStr, ForumType forum, Instructor instructor) {
        super(SessionType.Ninja, dateTimeStr, forum, instructor);
        this.price = 150;
        this.capacity = 5;
    }
}
