package dev.mai.services;

import java.util.List;

import dev.mai.models.Client;
import dev.mai.repositories.ClientRepo;

public class ClientServiceImpl implements ClientService {
	
	private ClientRepo clientRepo;
	
	public ClientServiceImpl(ClientRepo cr) {
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

}
