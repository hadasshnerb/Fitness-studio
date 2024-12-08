package gym.observer;

import java.util.ArrayList;
import java.util.List;

public class Sender {
    private List<Receiver> receivers;

    public Sender() {
        receivers = new ArrayList<>();
    }

    public void attach(Receiver receiver) {
        if (!receivers.contains(receiver)) {
            receivers.add(receiver);
        }
    }

    public void detach(Receiver receiver) {
        receivers.remove(receiver);
    }

    public void notifyReceivers(String message) {
        for (Receiver receiver : receivers) {
            receiver.update(message);
        }
    }
}
