
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankAccount {

	protected String accountNumber;
	protected double balance;
	private List<Transaction> transactionHistory;
	private List<BankAccount> accounts;
	private List<Transaction> transactions;

	public BankAccount(String accountNumber, double balance) {
		setAccountNumber(accountNumber);
		setBalance(balance);
		setTransactionHistory();
		setAccounts();
		setTransactions();

	}

	private void setTransactions() {
		this.transactions = new ArrayList<>();
		
	}

	private void setAccounts() {
		this.accounts = new ArrayList<>();

	}

	private void setTransactionHistory() {
		this.transactionHistory = new ArrayList<>();

	}

	private void setBalance(double balance) {
		this.balance = balance;

	}

	private void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("El monto a retirar debe ser mayor a 0.");
		}
		if (amount > this.balance) {
			System.err.println("Error: no hay suficiente saldo disponible para realizar la operación");
			return;
		}
		this.balance -= amount;
		addTransactionToHistory("withdraw", amount);
	}

	private void addTransactionToHistory(String type, double amount) {
		Transaction transaction = new Transaction(type, amount);
		transactionHistory.add(transaction);
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0.");
		}
		balance += amount;
	}

	public double getBalance() {
		return balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getType() {
		return this instanceof CheckingAccount ? "Cuenta corriente"
				: this instanceof SavingsAccount ? "Cuenta de ahorro" : "Cuenta ";
	}

	public List<BankAccount> getAccounts() {
	    
		  return Collections.unmodifiableList(accounts);
	}
	
	public void transfer(BankAccount destinationAccount, double amount) throws Exception {
		if (amount <= 0) {
			throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
		}
		if (destinationAccount == null) {
			throw new IllegalArgumentException("La cuenta destino es inválida.");
		}
		if (amount > this.balance) {
			throw new Exception("No tienes suficiente saldo para realizar esta transferencia.");
		}
		this.balance -= amount;
		destinationAccount.balance += amount;

		// Crear una nueva transacción para la cuenta origen
		Transaction transaction = new Transaction("Transferencia a " + destinationAccount.getAccountNumber(), amount);
		this.transactions.add(transaction);

		// Crear una nueva transacción para la cuenta destino
		transaction = new Transaction("Transferencia desde " + this.getAccountNumber(), amount);
		destinationAccount.transactions.add(transaction);
	}

	public List<Transaction> getTransactions() {
	    
	    List<Transaction> transactionsCopy = new ArrayList<>(transactions);
	    return transactionsCopy;
	}

	public void addTransaction(Transaction transaction) {
	    if (getTransactions() == null) {
	        transactions = new ArrayList<>();
	    }
	    getTransactions().add(transaction);
	}

}
