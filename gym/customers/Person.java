package gym.customers;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Person implements Notification {
    private static int idCounter = 1111;
    protected int id;
    protected String name;
    protected Gender gender;
    protected LocalDate dateOfBirth;
    protected BankAccount bankAccount;
    protected List<String> notifications;

    public Person(String name, double initialBalance, Gender gender, String dateOfBirth) {
        this.id = idCounter++;
        this.name = name;
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);

        this.bankAccount = new BankAccount(initialBalance);
        this.notifications = new ArrayList<>();
    }
    public Person(Person other) {
        this.id = other.id;
        this.name = other.name;
        this.gender = other.gender;
        this.dateOfBirth = other.dateOfBirth;
        this.bankAccount = other.bankAccount;
        this.notifications = other.notifications;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public double getBalance() {
        return bankAccount.getBalance();
    }

    public void addBalance(double amount) {
        bankAccount.deposit(amount);
    }

    public void reduceBalance(double amount) {
        bankAccount.withdraw(amount);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public void addNotification(String message) {
        notifications.add(message);
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Name: " + name
                + " | Gender: " + gender
                + " | Birthday: " + dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + " | Age: " + getAge()
                + " | Balance: " + bankAccount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
