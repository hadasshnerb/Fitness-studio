package gym.management;

import gym.customers.Client;
import gym.customers.Person;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;

public class Gym {
    private static Gym instance;
    private String name;
    private Secretary secretary;
    private List<Client> clients;
    private List<Instructor> instructors;
    private List<Session> sessions;
    private List<String> actionHistory;
    private double balance;

    private Gym() {
        clients = new ArrayList<>();
        instructors = new ArrayList<>();
        sessions = new ArrayList<>();
        actionHistory = new ArrayList<>();
        balance = 0;
    }

    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    public void setSecretary(Person person, double salary) {
        if (this.secretary == null) {
            this.secretary = new Secretary(person, salary, this);
            addAction("A new secretary has started working at the gym: " + person.getName());
        } else {
            this.secretary.deactivate();
            this.secretary = new Secretary(person, salary, this);
            addAction("A new secretary has started working at the gym: " + person.getName());
        }
    }


    public Secretary getSecretary() {
        return secretary;
    }

    public boolean isClientRegistered(Client client) {
        return clients.contains(client);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void addAction(String action) {
        actionHistory.add(action);
    }

    public List<String> getActionHistory() {
        return actionHistory;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setName(String s){
        this.name = s;
    }

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
