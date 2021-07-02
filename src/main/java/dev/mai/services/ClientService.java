package dev.mai.services;

import java.util.List;

import dev.mai.models.Client;

public interface ClientService {
	
	
	public Client getClient(int id);
	public List<Client> getAllClients();
	public Client addClient(Client Client);
	public Client updateClient(Client changeClient);
	public Client deleteClient(int id);

	
}
