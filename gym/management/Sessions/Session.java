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

/**
 * Represents a gym session.
 * Serves as a base class for specific types of sessions, providing common properties and methods.
 */
public abstract class Session extends Sender {
    protected SessionType type;
    protected LocalDateTime dateTime;
    protected ForumType forum;
    protected Instructor instructor;
    protected int capacity;
    protected double price;
    protected List<Client> participants;

    /**
     * Constructs a session with the specified details.
     *
     * @param type        the type of session
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     */
    protected Session(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) {
        super();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.type = type;
        this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        this.forum = forum;
        this.instructor = Objects.requireNonNull(instructor, "Instructor cannot be null");
        this.participants = new ArrayList<>();
    }

    /**
     * Gets the session type.
     *
     * @return the session type
     */
    public SessionType getType() {
        return type;
    }

    /**
     * Checks if the session is scheduled for a future date and time.
     *
     * @return true if the session is in the future, false otherwise
     */
    public boolean isInFuture() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return this.dateTime.isAfter(currentDateTime);
    }

    /**
     * Gets the session date and time.
     *
     * @return the session date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the forum type of the session.
     *
     * @return the forum type
     */
    public ForumType getForum() {
        return forum;
    }

    /**
     * Gets the instructor for the session.
     *
     * @return the instructor
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Gets the price of the session.
     *
     * @return the session price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Checks if the session is full.
     *
     * @return true if the session has no available spots, false otherwise
     */
    public boolean isFull() {
        return this.participants.size() >= this.capacity;
    }

    /**
     * Checks if a client is registered for the session.
     *
     * @param client the client to check
     * @return true if the client is registered, false otherwise
     */
    public boolean isClientRegistered(Client client) {
        return participants.contains(client);
    }

    /**
     * Checks if a client is eligible to join the session based on forum rules.
     *
     * @param client the client to check
     * @return true if the client is eligible, false otherwise
     */
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

    /**
     * Registers a client for the session if there is available capacity.
     *
     * @param client the client to register
     * @throws IllegalStateException if the session is full
     */
    public void registerClient(Client client) {
        if (isFull()) {
            throw new IllegalStateException("No available spots for session");
        }
        participants.add(client);
        attach(client); // For notifications
    }

    /**
     * Returns a string representation of the session details.
     *
     * @return a string with the session details
     */
    @Override
    public String toString() {
        return "Session Type: " + type +
                " | Date: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) +
                " | Forum: " + forum +
                " | Instructor: " + instructor.getName() +
                " | Participants: " + participants.size() + "/" + capacity;
    }

    /**
     * Checks if this session is equal to another object based on type, date, forum, and instructor.
     *
     * @param obj the object to compare
     * @return true if the sessions are equal, false otherwise
     */
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

    /**
     * Generates a hash code for the session based on type, date, forum, and instructor.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, dateTime, forum, instructor);
    }
}
