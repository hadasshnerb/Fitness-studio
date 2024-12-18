package gym.management;

import gym.customers.Client;
import gym.customers.Person;
import gym.management.Sessions.Session;
import gym.observer.Sender;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a gym management system.
 * Handles clients, instructors, sessions, and administrative actions.
 * Implements the Singleton design pattern to ensure only one instance of the gym exists.
 */
public class Gym extends Sender { // Gym now extends Sender
    private static Gym instance;
    private String name;
    private Secretary secretary;
    private List<Client> clients;
    private List<Instructor> instructors;
    private List<Session> sessions;
    private List<String> actionHistory;
    private double balance;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes lists for clients, instructors, sessions, and action history.
     */
    private Gym() {
        clients = new ArrayList<>();
        instructors = new ArrayList<>();
        sessions = new ArrayList<>();
        actionHistory = new ArrayList<>();
        balance = 0;
    }

    /**
     * Returns the single instance of the Gym.
     *
     * @return the Gym instance
     */
    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    /**
     * Sets or replaces the secretary for the gym.
     *
     * @param person the person to assign as secretary
     * @param salary the salary of the secretary
     */
    public void setSecretary(Person person, double salary) {
        if (this.secretary != null) {
            this.secretary.deactivate();
        }
        this.secretary = Secretary.createSecretary(person, salary, this);
        addAction("A new secretary has started working at the gym: " + person.getName());
    }

    /**
     * Returns the current secretary of the gym.
     *
     * @return the secretary
     */
    public Secretary getSecretary() {
        return secretary;
    }

    /**
     * Checks if a client is registered in the gym.
     *
     * @param client the client to check
     * @return true if the client is registered, false otherwise
     */
    public boolean isClientRegistered(Client client) {
        return clients.contains(client);
    }

    /**
     * Adds a new client to the gym.
     *
     * @param client the client to add
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Removes a client from the gym.
     *
     * @param client the client to remove
     */
    public void removeClient(Client client) {
        clients.remove(client);
    }

    /**
     * Returns the list of all clients registered in the gym.
     *
     * @return the list of clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Adds a new instructor to the gym.
     *
     * @param instructor the instructor to add
     */
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    /**
     * Returns the list of all instructors in the gym.
     *
     * @return the list of instructors
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }

    /**
     * Adds a new session to the gym's schedule.
     *
     * @param session the session to add
     */
    public void addSession(Session session) {
        sessions.add(session);
    }

    /**
     * Returns the list of all sessions in the gym.
     *
     * @return the list of sessions
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Adds an action to the gym's action history.
     *
     * @param action the action to record
     */
    public void addAction(String action) {
        actionHistory.add(action);
    }

    /**
     * Returns the list of all recorded actions.
     *
     * @return the action history
     */
    public List<String> getActionHistory() {
        return actionHistory;
    }

    /**
     * Adds an amount to the gym's balance.
     *
     * @param amount the amount to add
     */
    public void addBalance(double amount) {
        balance += amount;
    }

    /**
     * Deducts an amount from the gym's balance.
     *
     * @param amount the amount to deduct
     */
    public void deductBalance(double amount) {
        balance -= amount;
    }

    /**
     * Sets the gym's name.
     *
     * @param s the name to set
     */
    public void setName(String s) {
        this.name = s;
    }

    /**
     * Sends a notification to all clients in the gym.
     *
     * @param message the notification message
     */
    public void notifyAllClients(String message) {
        for (Client c : clients) {
            attach(c);
        }
        notifyReceivers(message);
        clearReceivers();
    }

    /**
     * Sends a notification to all participants of a specific session.
     *
     * @param session the session whose participants will be notified
     * @param message the notification message
     */
    public void notifySessionParticipants(Session session, String message) {
        session.getParticipants().forEach(this::attach);
        notifyReceivers(message);
        clearReceivers();
    }

    /**
     * Sends a notification to all participants in sessions on a specific date.
     *
     * @param dateStr the date in "dd-MM-yyyy" format
     * @param message the notification message
     */
    public void notifySessionsOnDate(String dateStr, String message) {
        for (Session s : sessions) {
            if (s.getDateTime().toLocalDate().equals(java.time.LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
                s.getParticipants().forEach(this::attach);
            }
        }
        notifyReceivers(message);
        clearReceivers();
    }

    /**
     * Clears the list of receivers attached to the gym.
     */
    private void clearReceivers() {
        getReceivers().clear();
    }

    /**
     * Returns a string representation of the gym's data, including clients, instructors, and sessions.
     *
     * @return a string with the gym's details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gym Name: ").append(name).append("\n");
        sb.append("Gym Secretary: ").append(secretary).append("\n");
        sb.append("Gym Balance: ").append((int) balance).append("\n\n");

        sb.append("Clients Data:\n");
        for (Client client : clients) {
            sb.append(client).append("\n");
        }

        sb.append("\nEmployees Data:\n");
        for (Instructor instructor : instructors) {
            sb.append(instructor).append("\n");
        }
        sb.append(secretary).append("\n");

        sb.append("\nSessions Data:\n");
        for (int i = 0; i < sessions.size(); i++) {
            sb.append(sessions.get(i));
            if (i < sessions.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
