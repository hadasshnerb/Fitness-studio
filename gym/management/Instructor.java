package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.List;

/**
 * Represents an instructor in the gym.
 * Extends the Person class and adds salary and certifications for sessions.
 */
public class Instructor extends Person {
    private double salaryPerHour;
    private List<SessionType> certifiedSessions;

    /**
     * Creates a new Instructor with personal details, salary, and session certifications.
     *
     * @param person           the Person object containing basic details
     * @param salaryPerHour    the hourly salary of the instructor
     * @param certifiedSessions the list of session types the instructor is certified to teach
     */
    public Instructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        super(person);  // Copy constructor of Person, sharing the same BankAccount
        this.salaryPerHour = salaryPerHour;
        this.certifiedSessions = certifiedSessions;
    }

    /**
     * Checks if the instructor is certified to teach a specific session type.
     *
     * @param type the session type to check
     * @return true if the instructor is certified, false otherwise
     */
    public boolean isCertifiedFor(SessionType type) {
        return certifiedSessions.contains(type);
    }

    /**
     * Gets the hourly salary of the instructor.
     *
     * @return the salary per hour
     */
    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    /**
     * Returns a string representation of the instructor, including personal details,
     * role, salary, and certifications.
     *
     * @return a string with the instructor's details
     */
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
            sb.setLength(sb.length() - 2); // Remove trailing ", "
        }
        return sb.toString();
    }
}
