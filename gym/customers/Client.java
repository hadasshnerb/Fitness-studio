package gym.customers;

import gym.observer.Receiver;

/**
 * Represents a client of the gym who can receive notifications and inherits basic properties from Person.
 * Implements the Receiver interface for handling notifications.
 */
public class Client extends Person implements Receiver {

    /**
     * Constructs a new Client based on an existing Person.
     *
     * @param person the person to be converted into a client
     */
    public Client(Person person) {
        super(person);
    }

    /**
     * Handles a notification message by adding it to the client's notifications list.
     *
     * @param message the notification message to add
     */
    @Override
    public void update(String message) {
        this.addNotification(message);
    }

    /**
     * Returns a string representation of the client.
     * This method uses the toString method from the superclass Person.
     *
     * @return a string containing the client's details
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
