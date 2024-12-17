package gym.management;

import gym.Exception.*;
import gym.customers.Client;
import gym.customers.Person;
import gym.customers.Gender;
import gym.management.Sessions.*;
import gym.observer.Sender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Secretary class represents the administrative role in a gym.
 * It manages operations such as client registration, session management,
 * notifications, and salary handling.
 */
public class Secretary extends Person {
    private double salary;       // Monthly salary of the secretary
    private Gym gym;            // Reference to the gym the secretary works for
    private boolean active;      // Indicates if the secretary is currently active
    private Sender sender;       // Used for managing notifications

    /**
     * Constructs a new Secretary instance.
     *
     * @param person The Person instance to base this Secretary on.
     * @param salary The monthly salary of the secretary.
     * @param gym    The gym instance the secretary is associated with.
     */
    public Secretary(Person person, double salary, Gym gym) {
        super(person);
        this.salary = salary;
        this.gym = gym;
        this.active = true;
        this.sender = new Sender();
        gym.addAction("A new secretary has started working at the gym: " + this.getName());
    }

    /**
     * Deactivates the secretary, preventing any further actions.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Ensures the secretary is active, throwing an exception if not.
     */
    private void checkActive() {
        if (!active) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        }
    }

    /**
     * Registers a new client in the gym.
     *
     * @param person The person to register as a client.
     * @return The newly created Client object.
     * @throws DuplicateClientException If the client is already registered.
     * @throws InvalidAgeException      If the client's age is less than 18.
     */
    public Client registerClient(Person person) throws DuplicateClientException, InvalidAgeException {
        checkActive();
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
     * @param client The client to unregister.
     * @throws ClientNotRegisteredException If the client is not registered in the gym.
     */
    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        checkActive();
        if (!gym.isClientRegistered(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        gym.removeClient(client);
        gym.addAction("Unregistered client: " + client.getName());
    }

    /**
     * Hires a new instructor for the gym.
     *
     * @param person            The person to hire as an instructor.
     * @param salaryPerHour     The hourly salary of the instructor.
     * @param certifiedSessions The list of session types the instructor is certified to teach.
     * @return The newly created Instructor object.
     */
    public Instructor hireInstructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        checkActive();
        Instructor instructor = new Instructor(person, salaryPerHour, certifiedSessions);
        gym.addInstructor(instructor);
        gym.addAction("Hired new instructor: " + instructor.getName() + " with salary per hour: " + (int) salaryPerHour);
        return instructor;
    }

    /**
     * Creates a new session in the gym.
     *
     * @param type        The type of the session.
     * @param dateTimeStr The date and time of the session in "dd-MM-yyyy HH:mm" format.
     * @param forum       The forum type of the session.
     * @param instructor  The instructor conducting the session.
     * @return The newly created Session object.
     * @throws InstructorNotQualifiedException If the instructor is not certified for the session type.
     */
    public Session addSession(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) throws InstructorNotQualifiedException {
        checkActive();
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
     * Registers a client to a session.
     *
     * @param client  The client to register.
     * @param session The session to register the client to.
     * @throws ClientNotRegisteredException If the client is not registered in the gym.
     * @throws DuplicateClientException     If the client is already registered for the session.
     */
    public void registerClientToLesson(Client client, Session session) throws ClientNotRegisteredException, DuplicateClientException {
        checkActive();

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
     * Sends a notification to everyone registered in a specific session.
     *
     * @param session The session to notify participants about.
     * @param message The notification message.
     */
    public void notify(Session session, String message) {
        checkActive();
        session.notifyReceivers(message);
        gym.addAction("A message was sent to everyone registered for session "
                + session.getType() + " on "
                + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
                + " : " + message);
    }

    /**
     * Sends a notification to all sessions on a specific date.
     *
     * @param dateStr The date in "dd-MM-yyyy" format.
     * @param message The notification message.
     */
    public void notify(String dateStr, String message) {
        checkActive();
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        for (Session s : gym.getSessions()) {
            if (s.getDateTime().toLocalDate().equals(date)) {
                s.notifyReceivers(message);
            }
        }
        gym.addAction("A message was sent to everyone registered for a session on "
                + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + " : " + message);
    }

    /**
     * Sends a notification to all clients in the gym.
     *
     * @param message The notification message.
     */
    public void notify(String message) {
        checkActive();
        for (Client c : gym.getClients()) {
            sender.attach(c);
        }
        sender.notifyReceivers(message);
        for (Client c : gym.getClients()) {
            sender.detach(c);
        }
        gym.addAction("A message was sent to all gym clients: " + message);
    }

    /**
     * Pays salaries to all instructors and the secretary.
     */
    public void paySalaries() {
        checkActive();
        for (Instructor instructor : gym.getInstructors()) {
            double totalHours = gym.getSessions().stream()
                    .filter(s -> s.getInstructor().equals(instructor))
                    .count();
            double instructorSalary = totalHours * instructor.getSalaryPerHour();
            instructor.addBalance(instructorSalary);
            gym.deductBalance(instructorSalary);
        }
        this.addBalance(this.salary);
        gym.deductBalance(this.salary);

        gym.addAction("Salaries have been paid to all employees");
    }

    /**
     * Prints the action history of the gym.
     */
    public void printActions() {
        checkActive();
        for (String action : gym.getActionHistory()) {
            System.out.println(action);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: Secretary | Salary per Month: " + (int) salary;
    }
}
