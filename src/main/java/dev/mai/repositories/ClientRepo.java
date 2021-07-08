package dev.mai.repositories;

import java.util.List;

import dev.mai.models.Client;


public interface ClientRepo {
	
	public Client getClient(int id);
	
	public List<Client> getAllClients();
	
	public Client addClient(Client m); 
	
	public Client updateClient(Client changeClient);
	
	public Client deleteClient(int id);

//	public void addClientAccount(Object in);

}
