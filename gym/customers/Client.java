package gym.customers;

import gym.observer.Receiver;

public class Client extends Person implements Receiver {

    public Client(Person person) {
        super(person);
    }
    @Override
    public void update(String message) {
        // קוראים ל-addNotification של Person ישירות
        this.addNotification(message);
    }

    @Override
    public String toString() {
        // בנייה דרך השדות בירושה מ-Person:
        return super.toString();
    }
}
