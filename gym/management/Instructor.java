package gym.management;

import gym.customers.Person;
import gym.customers.Gender;
import gym.management.Sessions.SessionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Instructor extends Person {
    private double salaryPerHour;
    private List<SessionType> certifiedSessions;
    private Person originalPerson;

    public Instructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        super(person.getName(), person.getBalance(), person.getGender(),
                person.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.id = person.getId();
        this.originalPerson = person;
        this.salaryPerHour = salaryPerHour;
        this.certifiedSessions = certifiedSessions;
    }

    public boolean isCertifiedFor(SessionType type) {
        return certifiedSessions.contains(type);
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public List<SessionType> getCertifiedSessions() {
        return certifiedSessions;
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
    public Gender getGender() {
        return originalPerson.getGender();
    }

    @Override
    public LocalDate getDateOfBirth() {
        return originalPerson.getDateOfBirth();
    }

    @Override
    public int getAge() {
        return originalPerson.getAge();
    }

    @Override
    public List<String> getNotifications() {
        return originalPerson.getNotifications();
    }

    @Override
    public void addNotification(String message) {
        originalPerson.addNotification(message);
    }

    @Override
    public String toString() {
        String base = "ID: " + originalPerson.getId() + " | Name: " + originalPerson.getName() +
                " | Gender: " + originalPerson.getGender() + " | Birthday: " +
                originalPerson.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                " | Age: " + originalPerson.getAge() + " | Balance: " + (int) originalPerson.getBalance();
        StringBuilder sb = new StringBuilder(base);
        sb.append(" | Role: Instructor | Salary per Hour: ").append((int) salaryPerHour).append(" | Certified Classes: ");
        for (SessionType type : certifiedSessions) {
            sb.append(type).append(", ");
        }
        sb.setLength(sb.length() - 2); // Remove trailing comma and space
        return sb.toString();
    }
}
