package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Instructor extends Person {
    private double salaryPerHour;
    private List<SessionType> certifiedSessions;

    public Instructor(Person person, double salaryPerHour, List<SessionType> certifiedSessions) {
        super(person.getName(), person.getBalance(), person.getGender(), person.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.id = person.getId();
        this.salaryPerHour = salaryPerHour;
        this.certifiedSessions = certifiedSessions;
    }

    public boolean isCertifiedFor(SessionType type) {
        return certifiedSessions.contains(type);
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public List<SessionType> getCertifiedSessions() {
        return certifiedSessions;
    }

    @Override
    public String toString() {
        String roleInfo = " | Role: Instructor | Salary per Hour: " + (int) salaryPerHour + " | Certified Classes: ";
        for (SessionType type : certifiedSessions) {
            roleInfo += type + ", ";
        }
        roleInfo = roleInfo.substring(0, roleInfo.length() - 2); // Remove last comma
        return super.toString() + roleInfo;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Instructor other = (Instructor) obj;
        return this.id == other.id; // השוואה לפי ID ייחודי
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

