package gym.management.Sessions;

import gym.customers.Client;
import gym.customers.Gender;
import gym.management.Instructor;
import gym.observer.Sender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Session extends Sender {
    protected SessionType type;
    protected LocalDateTime dateTime;
    protected ForumType forum;
    protected Instructor instructor;
    protected int capacity;
    protected double price;
    protected List<Client> participants;

    protected Session(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) {
        super();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.type = type;
        this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        this.forum = forum;
        this.instructor = Objects.requireNonNull(instructor, "Instructor cannot be null");
        this.participants = new ArrayList<>();
    }

    public SessionType getType() {
        return type;
    }

    public boolean isInFuture() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return this.dateTime.isAfter(currentDateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ForumType getForum() {
        return forum;
    }

    public Instructor getInstructor() {
        return instructor;
    }
    public double getPrice() {
        return price;
    }
    public boolean isFull() {
        return this.participants.size() >= this.capacity;
    }

    public boolean isClientRegistered(Client client) {
        return participants.contains(client);
    }

    public boolean isClientEligible(Client client) {
        switch (forum) {
            case Male:
                return client.getGender() == Gender.Male;
            case Female:
                return client.getGender() == Gender.Female;
            case Seniors:
                return client.getAge() >= 65;
            case All:
                return true;
            default:
                return false;
        }
    }

    public void registerClient(Client client) {
        if (isFull()) {
            throw new IllegalStateException("No available spots for session");
        }
        participants.add(client);
        attach(client); // For notifications
    }

    @Override
    public String toString() {
        return "Session Type: " + type +
                " | Date: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) +
                " | Forum: " + forum +
                " | Instructor: " + instructor.getName() +
                " | Participants: " + participants.size() + "/" + capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Session session = (Session) obj;

        return type == session.type &&
                dateTime.equals(session.dateTime) &&
                forum == session.forum &&
                instructor.equals(session.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, dateTime, forum, instructor);
    }
}
