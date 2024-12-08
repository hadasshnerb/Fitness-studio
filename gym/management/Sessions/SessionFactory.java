package gym.management.Sessions;

import gym.management.Instructor;

public class SessionFactory {
    public static Session createSession(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) {
        return new Session(type, dateTimeStr, forum, instructor);
    }
}
