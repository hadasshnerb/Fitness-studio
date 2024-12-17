package gym.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of receivers and sends notifications to them.
 * Used for handling communication in the gym system.
 */
public class Sender {
    private List<Receiver> receivers;

    /**
     * Creates a new Sender with an empty list of receivers.
     */
    public Sender() {
        receivers = new ArrayList<>();
    }

    /**
     * Attaches a new receiver to the list.
     *
     * @param receiver the receiver to add
     */
    public void attach(Receiver receiver) {
        if (!receivers.contains(receiver)) {
            receivers.add(receiver);
        }
    }

    /**
     * Detaches a receiver from the list.
     *
     * @param receiver the receiver to remove
     */
    public void detach(Receiver receiver) {
        receivers.remove(receiver);
    }

    /**
     * Sends a notification message to all attached receivers.
     *
     * @param message the message to send
     */
    public void notifyReceivers(String message) {
        for (Receiver receiver : receivers) {
            receiver.update(message);
        }
    }
}
