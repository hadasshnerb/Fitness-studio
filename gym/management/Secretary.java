package gym.management;

import gym.Exception.*;
import gym.customers.Client;
import gym.customers.Gender;
import gym.customers.Person;
import gym.management.Sessions.*;
import gym.observer.Receiver;
import gym.observer.Sender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Secretary extends Person {
    private double salary;
    private Gym gym;
    private boolean active;
    private Sender sender;
    private final Map<Client, Set<Session>> registrationAttempts = new HashMap<>();


    public Secretary(Person person, double salary, Gym gym) {
        super(person.getName(), person.getBalance(), person.getGender(), person.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.id = person.getId();
        this.salary = salary;
        this.gym = gym;
        this.active = true;
        this.sender = new Sender();
    }

    public void deactivate() {
        this.active = false;
    }

    private void checkActive() {
        if (!active) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        }
    }

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

    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        checkActive();
        if (!gym.isClientRegistered(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        gym.removeClient(client);
        gym.addAction("Unregistered client: " + client.getName());
    }

    public Instructor hireInstructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        checkActive();
        Instructor instructor = new Instructor(person, salaryPerHour, certifiedSessions);
        gym.addInstructor(instructor);
        gym.addAction("Hired new instructor: " + instructor.getName() + " with salary per hour: " + (int) salaryPerHour);
        return instructor;
    }

    public Session addSession(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) throws InstructorNotQualifiedException {
        checkActive();
        if (!instructor.isCertifiedFor(type)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        Session session = SessionFactory.createSession(type, dateTimeStr, forum, instructor);
        gym.addSession(session);
        gym.addAction("Created new session: " + type + " on " + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) + " with instructor: " + instructor.getName());
        return session;
    }

    public void registerClientToLesson(Client client, Session session) throws ClientNotRegisteredException, DuplicateClientException {
        checkActive();

        // Check if the client is registered in the gym
        if (!gym.isClientRegistered(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        // Check if the client is already registered for the session
        if (session.isClientRegistered(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }

        // Handle additional checks if the initial checks pass
        List<String> errorMessages = collectErrorMessages(client, session);

        // If there are additional errors, log them and stop
        if (!errorMessages.isEmpty()) {
            for (String error : errorMessages) {
                gym.addAction("Failed registration: " + error);
            }
            return; // Stop further execution
        }

        // Complete the registration
        completeRegistration(client, session);
    }

    // Collects all additional errors after the first two checks
    private List<String> collectErrorMessages(Client client, Session session) {
        List<String> errors = new ArrayList<>();

        // Check if the session is in the future
        if (!session.isInFuture()) {
            errors.add("Session is not in the future");
        }

        // Check if the client is eligible for the session
        if (!session.isClientEligible(client)) {
            if (session.getForum() == ForumType.Seniors && client.getAge() < 65) {
                errors.add("Client doesn't meet the age requirements for this session (Seniors)");
            } else if ((session.getForum() == ForumType.Male && client.getGender() != Gender.Male) ||
                    (session.getForum() == ForumType.Female && client.getGender() != Gender.Female)) {
                errors.add("Client's gender doesn't match the session's gender requirements");
            } else {
                errors.add("Client is not eligible for this session");
            }
        }

        // Check if there are available spots
        if (session.isFull()) {
            errors.add("No available spots for session");
        }

        // Check if the client has enough balance
        if (client.getBalance() < session.getPrice()) {
            errors.add("Client doesn't have enough balance");
        }

        return errors;
    }

    // Completes the registration
    private void completeRegistration(Client client, Session session) {
        session.registerClient(client);
        client.deductBalance(session.getPrice());
        gym.addBalance(session.getPrice());
        gym.addAction("Registered client: " + client.getName() + " to session: " + session.getType()
                + " on " + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
                + " for price: " + (int) session.getPrice());
    }



    public void notify(Session session, String message) {
        checkActive();
        session.notifyReceivers(message);
        gym.addAction("A message was sent to everyone registered for session " + session.getType() + " on " + session.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) + " : " + message);
    }

    public void notify(String dateStr, String message) {
        checkActive();
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        for (Session session : gym.getSessions()) {
            if (session.getDateTime().toLocalDate().equals(date)) {
                session.notifyReceivers(message);
            }
        }
        gym.addAction("A message was sent to everyone registered for a session on " + dateStr + " : " + message);
    }

    public void notify(String message) {
        checkActive();
        // Use sender to notify all clients
        for (Client client : gym.getClients()) {
            sender.attach((Receiver) client);
        }
        sender.notifyReceivers(message);
        // Detach all clients to prevent duplicates in future notifications
        for (Client client : gym.getClients()) {
            sender.detach((Receiver) client);
        }
        gym.addAction("A message was sent to all gym clients: " + message);
    }

    public void paySalaries() {
        checkActive();
        for (Instructor instructor : gym.getInstructors()) {
            double totalHours = 0;
            // Calculate total hours based on sessions they have
            for (Session session : gym.getSessions()) {
                if (session.getInstructor().equals(instructor)) {
                    totalHours += 1; // Assuming each session is 1 hour
                }
            }
            double salary = totalHours * instructor.getSalaryPerHour();
            instructor.addBalance(salary);
            gym.deductBalance(salary);
        }
        // Pay secretary salary
        this.addBalance(this.salary);
        gym.deductBalance(this.salary);
        gym.addAction("Salaries have been paid to all employees");
    }


    public void printActions() {
        checkActive();
        for (String action : gym.getActionHistory()) {
            System.out.println(action);
        }
    }

    @Override
    public String toString() {
        String roleInfo = " | Role: Secretary | Salary per Month: " + (int) salary;
        return super.toString() + roleInfo;
    }
}
