package gym.customers;

import java.time.format.DateTimeFormatter;

import gym.observer.Receiver;

public class Client extends Person implements Receiver {
    private Person originalPerson;

    public Client(Person person) {
        super(person.getName(), person.getBalance(), person.getGender(),
                person.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.id = person.getId();
        this.originalPerson = person;
    }

    @Override
    public void update(String message) {
        originalPerson.addNotification(message);
    }

    @Override
    public double getBalance() {
        return originalPerson.getBalance();
    }

    @Override
    public void addBalance(double amount) {
        originalPerson.addBalance(amount);
    }

    @Override
    public void deductBalance(double amount) {
        originalPerson.deductBalance(amount);
    }

    @Override
    public String getName() {
        return originalPerson.getName();
    }

    @Override
    public int getAge() {
        return originalPerson.getAge();
    }

    @Override
    public java.time.LocalDate getDateOfBirth() {
        return originalPerson.getDateOfBirth();
    }

    @Override
    public java.util.List<String> getNotifications() {
        return originalPerson.getNotifications();
    }

    @Override
    public void addNotification(String message) {
        originalPerson.addNotification(message);
    }

    @Override
    public Gender getGender() {
        return originalPerson.getGender();
    }

    @Override
    public String toString() {
        // Build the string from originalPerson to ensure correct synchronization
        return "ID: " + originalPerson.getId() + " | Name: " + originalPerson.getName() +
                " | Gender: " + originalPerson.getGender() + " | Birthday: " +
                originalPerson.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                " | Age: " + originalPerson.getAge() + " | Balance: " + (int) originalPerson.getBalance();
    }
}
