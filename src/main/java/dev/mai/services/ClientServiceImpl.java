package dev.mai.services;

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

}
