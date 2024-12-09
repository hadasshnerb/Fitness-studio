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
    protected double balance;
    protected Gender gender;
    protected LocalDate dateOfBirth;
    protected List<String> notifications;

    public Person(String name, double balance, Gender gender, String dateOfBirth) {
        this.notifications = new ArrayList<>();
        this.id = idCounter++;
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);
        this.notifications = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        // כאן מניחים שהתאריך הנוכחי הוא 01-01-2025 לפי דרישות המשימה
        LocalDate today = LocalDate.of(2025, 1, 1);
        return Period.between(dateOfBirth, today).getYears();
    }

    public void deductBalance(double amount) {
        this.balance = balance - amount;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void setBalance(double amount){
        this.balance = amount;
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
        return "ID: " + id + " | Name: " + name + " | Gender: " + gender + " | Birthday: " +
                dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " | Age: " + getAge() +
                " | Balance: " + (int) balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
