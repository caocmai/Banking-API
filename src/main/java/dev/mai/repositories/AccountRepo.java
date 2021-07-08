package dev.mai.repositories;

import java.util.List;

import dev.mai.models.Account;

public interface AccountRepo {

	public Account getAccount(int id);
	
	public List<Account> getAllAccounts();
	
	public Account addAccount(Account m); 
	
	public Account updateAccount(Account changeAccount);
	
	public Account deleteAccount(int id);

}
