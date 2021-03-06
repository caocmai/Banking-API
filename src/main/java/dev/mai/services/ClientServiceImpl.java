package dev.mai.services;

import java.util.ArrayList;
import java.util.List;

import dev.mai.models.Account;
import dev.mai.models.Client;
//import dev.mai.repositories.ClientRepo;
import dev.mai.repositories.ClientRepoDBImpl;

public class ClientServiceImpl implements ClientService {

	private ClientRepoDBImpl clientRepo;

	public ClientServiceImpl(ClientRepoDBImpl cr) {
		this.clientRepo = cr;

	}

	@Override
	public Client getClient(int id) {
		return clientRepo.getClient(id);
	}

	@Override
	public List<Client> getAllClients() {
		return clientRepo.getAllClients();
	}

	@Override
	public Client addClient(Client client) {
		return clientRepo.addClient(client);
	}

	@Override
	public Client updateClient(Client changeClient) {
		return clientRepo.updateClient(changeClient);
	}

	@Override
	public Client deleteClient(int id) {
		return clientRepo.deleteClient(id);
	}

	@Override
	public Account addAccountToClient(int id) {
		Client c = clientRepo.getClient(id);
		if (c == null) {
			return null;
		}

		return clientRepo.addAccount(c);

	}

	@Override
	public List<Account> getAllAccountsFromClient(int id) {
		return clientRepo.getAllAccountsFromClient(id);
	}

	@Override
	public Account getAnAccount(int accountId) {
		return clientRepo.getAccount(accountId);
	}

	@Override
	public Account getAnAccount(int clientId, int accountId) {
		Client c = clientRepo.getClient(clientId);
		Account a = clientRepo.getAccount(accountId);
		if (c == null || a == null) {
			return null;
		}
		return a;
	}

	@Override
	public Account deleteAccount(int clientId, int accountId) {
		Client c = clientRepo.getClient(clientId);
		Account a = clientRepo.deleteAccount(accountId);
		if (c == null || a == null) {
			return null;
		}
		return a;
	}

	@Override
	public Account updateAccount(Account changeAccount) {
		return clientRepo.updateAccount(changeAccount);
	}

	@Override
	public Account updateAccount(int clientId, Account changeAccount) {
		Client c = clientRepo.getClient(clientId);
		Account a = clientRepo.getAccount(changeAccount.getId());

		if (c == null || a == null) {
			return null;
		}

		Account updatedAcc = clientRepo.updateAccount(changeAccount);
		return updatedAcc;
	}

	@Override
	public List<Account> getAccountsBetween(int clientId, double min, double max) {
		Client c = clientRepo.getClient(clientId);
		if (c == null) {
			return null;
		}

		List<Account> accs = clientRepo.getAllAccountsFromClient(c.getId());
		List<Account> filteredAccs = new ArrayList<Account>();

		for (Account a : accs) {
			if (a != null) {
				double balance = a.getBalance();
				if (balance >= min & balance <= max) {
					filteredAccs.add(a);
				}
			}
		}

		return filteredAccs;
	}

	@Override
	public Account deposit(int clientId, int accountId, double amount) {
		Client c = clientRepo.getClient(clientId);
		Account a = this.getAnAccount(clientId, accountId);
		
		if (a == null || c == null) {
			return null;
		}
		
		a.setBalance(a.getBalance()+amount);
		a = this.updateAccount(c.getId(), a);
		
		return a;
	}

	@Override
	public Account withdraw(int clientId, int accountId, double amount) {
		Client c = clientRepo.getClient(clientId);
		Account a = this.getAnAccount(clientId, accountId);
		
		if (a == null || c == null) {
			return null;
		}
		
		if (a.getBalance() < amount) {
			return a;
		}
		a.setBalance(a.getBalance()-amount);
		a = this.updateAccount(c.getId(), a);
		
		return a;
	}

	@Override
	public Account transfer(int fromClientId, int fromClientAcc, int toAccountId, double amount) {
		Account fromAcc = this.getAnAccount(fromClientId, fromClientAcc);
		Account toAcc = this.getAnAccount(toAccountId);
		
		if (fromAcc == null || toAcc == null) {
			return null;
		}
		
		if (fromAcc.getBalance() < amount) {
			return fromAcc;
		}
		fromAcc.setBalance(fromAcc.getBalance()-amount);
		toAcc.setBalance(toAcc.getBalance()+amount);
		
		fromAcc = this.updateAccount(fromAcc.getId(), fromAcc);
		toAcc = this.updateAccount(toAcc);
		
		return toAcc;
	}

}
