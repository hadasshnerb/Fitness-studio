package gym.management.Sessions;

import gym.management.Instructor;

public class SessionFactory {
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
