package gym.customers;

import gym.observer.Receiver;

public class Client extends Person implements Receiver {

    public Client(Person person) {
        super(person);
    }
    @Override
    public void update(String message) {
        this.addNotification(message);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
