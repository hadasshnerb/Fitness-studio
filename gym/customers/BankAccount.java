package gym.customers;

/**
 * A simple bank account for managing balance.
 */
public class BankAccount {
    private double balance;

    /**
     * Creates a bank account with an initial balance.
     *
     * @param initialBalance the starting balance
     */
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Returns the current balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Adds money to the account.
     *
     * @param amount the amount to add
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Removes money from the account.
     *
     * @param amount the amount to subtract
     */
    public void withdraw(double amount) {
        balance -= amount;
    }

    /**
     * Returns the balance as a string, rounded to the nearest integer.
     *
     * @return the balance as a string
     */
    @Override
    public String toString() {
        return String.valueOf((int) balance);
    }
}
