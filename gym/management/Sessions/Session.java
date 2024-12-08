package gym.management.Sessions;

import gym.customers.Client;
import gym.customers.Gender;
import gym.management.Instructor;
import gym.observer.Receiver;
import gym.observer.Sender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Session extends Sender {
    private SessionType type;
    private LocalDateTime dateTime;
    private ForumType forum;
    private Instructor instructor;
    private int capacity;
    private double price;
    private List<Client> participants;

    protected Session(SessionType type, String dateTimeStr, ForumType forum, Instructor instructor) {
        super();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.type = type;
        this.dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        this.forum = forum;
        this.instructor = instructor;
        this.participants = new ArrayList<>();

        // Set capacity and price based on SessionType
        switch (type) {
            case Pilates:
                this.price = 60;
                this.capacity = 30;
                break;
            case MachinePilates:
                this.price = 80;
                this.capacity = 10;
                break;
            case ThaiBoxing:
                this.price = 100;
                this.capacity = 20;
                break;
            case Ninja:
                this.price = 150;
                this.capacity = 5;
                break;
            default:
                break;
        }
    }

    public SessionType getType() {
        return type;
    }

    public boolean isInFuture() {
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
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

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public List<Client> getParticipants() {
        return participants;
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
        participants.add(client);
        attach(client); // For notifications
    }

    @Override
    public String toString() {
        return "Session Type: " + type + " | Date: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) +
                " | Forum: " + forum + " | Instructor: " + instructor.getName() +
                " | Participants: " + participants.size() + "/" + capacity;
    }
}
