import java.util.ArrayList;
import java.util.Scanner;

class Bankaccount {
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;
    private final ArrayList<String> transactionhistory;
    public Bankaccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionhistory = new ArrayList<>();
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
        transactionhistory.add("Deposit: +" + amount);
    }
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionhistory.add("Withdrawal: -" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds!");
            return false;
        }
    }
    public ArrayList<String> gettransactionhistory() {
        return transactionhistory;
    }
}

public class Main {
    private static final ArrayList<Bankaccount> accounts = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("1. create account");
            System.out.println("2. check balance");
            System.out.println("3. deposit");
            System.out.println("4. withdraw");
            System.out.println("5. transfer funds");
            System.out.println("6. transaction history");
            System.out.println("7. exit");
            System.out.print("enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: createaccount();
                    break;
                case 2: checkbalance();
                    break;
                case 3: deposit();
                    break;
                case 4: withdraw();
                    break;
                case 5: transferFunds();
                    break;
                case 6: viewtransactionhistory();
                    break;
                case 7: System.out.println("Exiting the program");
                    break;
                default: System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }
    private static void createaccount() {
        System.out.print("Enter account number: ");
        String accountNumber = sc.next();
        System.out.print("Enter account holder name: ");
        String accountHolderName = sc.next();
        System.out.print("Enter initial balance: ");
        double initialBalance = sc.nextDouble();
        Bankaccount newAccount = new Bankaccount(accountNumber, accountHolderName, initialBalance);
        accounts.add(newAccount);
        System.out.println("Account created successfully!");
    }
    private static void checkbalance() {
        System.out.print("Enter account number: ");
        String accountNumber = sc.next();
        Bankaccount account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.println("Account Balance: " + account.getBalance());
        }
    }
    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = sc.next();
        Bankaccount account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = sc.nextDouble();
            account.deposit(amount);
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        }
    }
    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = sc.next();
        Bankaccount account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();
            if (account.withdraw(amount)) {
                System.out.println("Withdrawal successful. New balance: " + account.getBalance());
            }
        }
    }
    private static void transferFunds() {
        System.out.print("Enter sender account number: ");
        String senderAccountNumber = sc.next();
        Bankaccount senderAccount = findAccount(senderAccountNumber);
        System.out.print("Enter receiver account number: ");
        String receiverAccountNumber = sc.next();
        Bankaccount receiverAccount = findAccount(receiverAccountNumber);
        if (senderAccount != null && receiverAccount != null) {
            System.out.print("Enter transfer amount: ");
            double amount = sc.nextDouble();
            if (senderAccount.withdraw(amount)) {
                receiverAccount.deposit(amount);
                System.out.println("Transfer successful. New balance for sender: " + senderAccount.getBalance());
            }
        }
    }
    static void viewtransactionhistory() {
        System.out.print("Enter account number: ");
        String accountNumber = sc.next();
        Bankaccount account = findAccount(accountNumber);
        if (account != null) {
            ArrayList<String> transactions = account.gettransactionhistory();
            System.out.println("Transaction History for Account " + account.getAccountNumber());
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
    static Bankaccount findAccount(String accountNumber) {
        for (Bankaccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("account not found");
        return null;
    }
}
