package gym.management;

import gym.Exception.*;
import gym.customers.Client;
import gym.customers.Person;
import gym.customers.Gender;
import gym.management.Sessions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the secretary of a gym, responsible for managing clients,
 * instructors, sessions, and sending notifications through the Gym.
 */
public class Secretary extends Person {
    private double salary;
    private Gym gym; // The gym now is responsible for sending messages, not the secretary directly

    /**
     * Private constructor to initialize a secretary with the given person, salary, and gym.
     *
     * @param person the person representing the secretary
     * @param salary the monthly salary of the secretary
     * @param gym    the gym instance the secretary works for
     */
    private Secretary(Person person, double salary, Gym gym) {
        super(person);
        this.salary = salary;
        this.gym = gym;
    }

    /**
     * Factory method to create a secretary.
     *
     * @param person the person representing the secretary
     * @param salary the monthly salary of the secretary
     * @param gym    the gym instance the secretary works for
     * @return a new Secretary instance
     */
    protected static Secretary createSecretary(Person person, double salary, Gym gym) {
        return new Secretary(person, salary, gym);
    }

    /**
     * Deactivates the secretary by removing access to the gym.
     */
    public void deactivate() {
        this.gym = null;
    }

    /**
     * Registers a new client in the gym.
     *
     * @param person the person to register as a client
     * @return the newly registered Client instance
     * @throws DuplicateClientException if the client is already registered
     * @throws InvalidAgeException      if the client is under 18 years old
     */
    public Client registerClient(Person person) throws DuplicateClientException, InvalidAgeException {
        if (person.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }
        Client client = new Client(person);
        if (gym.isClientRegistered(client)) {
            throw new DuplicateClientException("Error: The client is already registered");
        }
        gym.addClient(client);
        gym.addAction("Registered new client: " + client.getName());
        return client;
    }

    /**
     * Unregisters a client from the gym.
     *
     * @param client the client to unregister
     * @throws ClientNotRegisteredException if the client is not registered
     */
    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        if (!gym.isClientRegistered(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        gym.removeClient(client);
        gym.addAction("Unregistered client: " + client.getName());
    }

    /**
     * Hires a new instructor for the gym.
     *
     * @param person            the person to hire as an instructor
     * @param salaryPerHour     the hourly salary of the instructor
     * @param certifiedSessions the list of session types the instructor is certified to teach
     * @return the newly hired Instructor instance
     */
    public Instructor hireInstructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        Instructor instructor = new Instructor(person, salaryPerHour, certifiedSessions);
        gym.addInstructor(instructor);
        gym.addAction("Hired new instructor: " + instructor.getName() + " with salary per hour: " + (int) salaryPerHour);
        return instructor;
    }

    /**
     * Creates a new session in the gym.
     *
     * @param type        the type of session
     * @param dateTimeStr the date and time of the session in "dd-MM-yyyy HH:mm" format
     * @param forum       the forum type of the session (e.g., Male, Female, Seniors, All)
     * @param instructor  the instructor leading the session
     * @return the newly created Session instance
     * @throws InstructorNotQualifiedException if the instructor is not certified for the session type
     */
    public Session addSession(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) throws InstructorNotQualifiedException {
        if (!instructor.isCertifiedFor(type)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        Session session = SessionFactory.createSession(type, dateTimeStr, forum, instructor);
        gym.addSession(session);
        gym.addAction("Created new session: " + type + " on "
                + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
                + " with instructor: " + instructor.getName());
        return session;
    }

    /**
     * Registers a client to a session/lesson.
     *
     * @param client  the client to register
     * @param session the session to register the client for
     * @throws ClientNotRegisteredException if the client is not registered in the gym
     * @throws DuplicateClientException     if the client is already registered for the session
     */
    public void registerClientToLesson(Client client, Session session) throws ClientNotRegisteredException, DuplicateClientException {
        if (!gym.isClientRegistered(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }
        if (session.isClientRegistered(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }

        List<String> errorMessages = collectErrorMessages(client, session);
        if (!errorMessages.isEmpty()) {
            for (String error : errorMessages) {
                gym.addAction("Failed registration: " + error);
            }
            return;
        }

        completeRegistration(client, session);
    }

    /**
     * Collects error messages if a client cannot be registered to a session.
     *
     * @param client  the client to check
     * @param session the session to check
     * @return a list of error messages
     */
    private List<String> collectErrorMessages(Client client, Session session) {
        List<String> errors = new ArrayList<>();
        if (!session.isInFuture()) {
            errors.add("Session is not in the future");
        }
        if (!session.isClientEligible(client)) {
            if (session.getForum() == ForumType.Seniors && client.getAge() < 65) {
                errors.add("Client doesn't meet the age requirements for this session (Seniors)");
            } else if ((session.getForum() == ForumType.Male && client.getGender() != Gender.Male)
                    || (session.getForum() == ForumType.Female && client.getGender() != Gender.Female)) {
                errors.add("Client's gender doesn't match the session's gender requirements");
            } else {
                errors.add("Client is not eligible for this session");
            }
        }
        if (session.isFull()) {
            errors.add("No available spots for session");
        }
        if (client.getBalance() < session.getPrice()) {
            errors.add("Client doesn't have enough balance");
        }
        return errors;
    }

    /**
     * Completes the registration of a client to a session.
     *
     * @param client  the client to register
     * @param session the session to register the client for
     */
    private void completeRegistration(Client client, Session session) {
        session.registerClient(client);
        client.reduceBalance(session.getPrice());
        gym.addBalance(session.getPrice());
        gym.addAction("Registered client: " + client.getName() + " to session: "
                + session.getType() + " on "
                + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
                + " for price: " + (int) session.getPrice());
    }

    /**
     * Sends a notification to participants of a specific session.
     *
     * @param session the session whose participants will be notified
     * @param message the notification message
     */
    public void notify(Session session, String message) {
        gym.notifySessionParticipants(session, message);
        gym.addAction("A message was sent to everyone registered for session "
                + session.getType() + " on "
                + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
                + " : " + message);
    }

    /**
     * Sends a notification to participants of all sessions on a specific date.
     *
     * @param dateStr the date in "dd-MM-yyyy" format
     * @param message the notification message
     */
    public void notify(String dateStr, String message) {
        gym.notifySessionsOnDate(dateStr, message);
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        gym.addAction("A message was sent to everyone registered for a session on "
                + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + " : " + message);
    }

    /**
     * Sends a general notification to all gym clients.
     *
     * @param message the notification message
     */
    public void notify(String message) {
        gym.notifyAllClients(message);
        gym.addAction("A message was sent to all gym clients: " + message);
    }

    /**
     * Pays the salaries of all instructors and the secretary.
     */
    public void paySalaries() {
        for (Instructor instructor : gym.getInstructors()) {
            double totalHours = gym.getSessions().stream().filter(s -> s.getInstructor().equals(instructor)).count();
            double instructorSalary = totalHours * instructor.getSalaryPerHour();
            instructor.addBalance(instructorSalary);
            gym.deductBalance(instructorSalary);
        }
        this.addBalance(this.salary);
        gym.deductBalance(this.salary);
        gym.addAction("Salaries have been paid to all employees");
    }

    /**
     * Prints the history of actions performed in the gym.
     */
    public void printActions() {
        for (String action : gym.getActionHistory()) {
            System.out.println(action);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: Secretary | Salary per Month: " + (int) salary;
    }
}
