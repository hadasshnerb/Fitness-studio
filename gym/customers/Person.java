package gym.customers;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Person class represents an individual in the gym system.
 * This class provides shared attributes and functionality for all individuals,
 * including clients, instructors, and staff members.
 */
public class Person implements Notification {
    private static int idCounter = 1111; // Static counter for generating unique IDs
    protected int id;                   // Unique ID of the person
    protected String name;              // Name of the person
    protected Gender gender;            // Gender of the person
    protected LocalDate dateOfBirth;    // Date of birth of the person
    protected BankAccount bankAccount;  // Bank account associated with the person
    protected List<String> notifications; // List of notifications for the person

    /**
     * Constructs a new Person with the specified attributes.
     *
     * @param name          The name of the person.
     * @param initialBalance The initial balance of the person's bank account.
     * @param gender        The gender of the person.
     * @param dateOfBirth   The date of birth of the person in "dd-MM-yyyy" format.
     */
    public Person(String name, double initialBalance, Gender gender, String dateOfBirth) {
        this.id = idCounter++;
        this.name = name;
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);

        this.bankAccount = new BankAccount(initialBalance);
        this.notifications = new ArrayList<>();
    }

    /**
     * Copy constructor that creates a new Person instance based on another Person.
     * Shares the same BankAccount and notifications.
     *
     * @param other The other Person instance to copy.
     */
    public Person(Person other) {
        this.id = other.id;
        this.name = other.name;
        this.gender = other.gender;
        this.dateOfBirth = other.dateOfBirth;
        this.bankAccount = other.bankAccount;
        this.notifications = other.notifications;
    }

    /**
     * Returns the name of the person.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the gender of the person.
     *
     * @return The gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Calculates and returns the age of the person based on the current date.
     *
     * @return The age of the person.
     */
    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Returns the current balance of the person's bank account.
     *
     * @return The balance.
     */
    public double getBalance() {
        return bankAccount.getBalance();
    }

    /**
     * Adds a specified amount to the person's bank account balance.
     *
     * @param amount The amount to add.
     */
    public void addBalance(double amount) {
        bankAccount.deposit(amount);
    }

    /**
     * Deducts a specified amount from the person's bank account balance.
     *
     * @param amount The amount to deduct.
     */
    public void reduceBalance(double amount) {
        bankAccount.withdraw(amount);
    }

    /**
     * Returns the list of notifications associated with the person.
     *
     * @return The list of notifications.
     */
    public List<String> getNotifications() {
        return notifications;
    }

    /**
     * Adds a notification message to the person's notification list.
     *
     * @param message The notification message.
     */
    @Override
    public void addNotification(String message) {
        notifications.add(message);
    }

    /**
     * Returns a string representation of the person.
     * Includes the ID, name, gender, date of birth, age, and balance.
     *
     * @return The string representation of the person.
     */
    @Override
    public String toString() {
        return "ID: " + id
                + " | Name: " + name
                + " | Gender: " + gender
                + " | Birthday: " + dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + " | Age: " + getAge()
                + " | Balance: " + bankAccount;
    }

    /**
     * Compares this person with another object for equality.
     * Two persons are considered equal if they have the same ID.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return id == other.id;
    }

    /**
     * Returns the hash code for the person, based on their ID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
