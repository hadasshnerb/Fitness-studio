package gym.customers;

import gym.observer.Receiver;
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
        addNotification(message);
    }

    @Override
    public void addBalance(double amount) {
        super.addBalance(amount);
        originalPerson.addBalance(amount); // Sync with the original person
    }

    @Override
    public void deductBalance(double amount) {
        super.deductBalance(amount);
        originalPerson.deductBalance(amount); // Sync with the original person
    }
}
