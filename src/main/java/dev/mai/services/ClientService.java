package dev.mai.services;

import java.util.List;

import dev.mai.models.Account;
import dev.mai.models.Client;

public interface ClientService {
	
	public Client getClient(int id);
	public List<Client> getAllClients();
	public Client addClient(Client Client);
	public Client updateClient(Client changeClient);
	public Client deleteClient(int id);
	
	public Account addAccountToClient(int id);
	public List<Account> getAllAccountsFromClient(int clientId);
	public Account getAnAccount(int accountId);
	public Account getAnAccount(int clientId, int accountId);
	public Account deleteAccount(int clientId, int accountId);
	public Account updateAccount(Account changeAccount);
	public Account updateAccount(int clientId, Account changeAccount);
	
	public List<Account> getAccountsBetween(int clientId, double min, double max);
	
	public Account deposit(int clientId, int accountId, double amount);
	public Account withdraw(int clientId, int accountId, double amount);
	public Account transfer(int fromClientId, int fromClientAcc, int toAccountId, double amount);

}
