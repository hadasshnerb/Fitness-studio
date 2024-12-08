package gym.customers;

import gym.observer.Receiver;

import java.time.format.DateTimeFormatter;

public class Client extends Person implements Receiver {
    public Client(Person person) {
        super(person.getName(), person.getBalance(), person.getGender(), person.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.id = person.getId();
    }

    @Override
    public void update(String message) {
        addNotification(message);
    }
}
