package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.List;

public class Instructor extends Person {
    private double salaryPerHour;
    private List<SessionType> certifiedSessions;

    public Instructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        super(person);  // copy constructor of Person -> shares the same BankAccount
        this.salaryPerHour = salaryPerHour;
        this.certifiedSessions = certifiedSessions;
    }

    public boolean isCertifiedFor(SessionType type) {
        return certifiedSessions.contains(type);
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" | Role: Instructor")
                .append(" | Salary per Hour: ").append((int) salaryPerHour)
                .append(" | Certified Classes: ");
        for (SessionType type : certifiedSessions) {
            sb.append(type).append(", ");
        }
        if (!certifiedSessions.isEmpty()) {
            sb.setLength(sb.length() - 2); // הסרת ", " בסוף
        }
        return sb.toString();
    }
}

