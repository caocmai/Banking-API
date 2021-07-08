package dev.mai.repositories;

import java.util.List;

import dev.mai.models.Account;
import dev.mai.models.Client;

public interface AccountRepo {

	public Account getAccount(int id);
	
	public List<Account> getAllAccounts();
	
	public Account addAccount(Client client); 
	
	public Account updateAccount(Account changeAccount);
	
	public Account deleteAccount(int id);

}
