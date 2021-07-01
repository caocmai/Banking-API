package dev.mai.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.mai.models.Client;
import dev.mai.util.FakeDB;


public class ClientRepoImpl implements ClientRepo {

	@Override
	public Client getClient(int id) {
		
		return FakeDB.clients.get(id);
	}

	@Override
	public List<Client> getAllClients() {
//		List<Client> allClients = new ArrayList<Client>();
//		for (Map.Entry<Integer,Client> c : FakeDB.clients.entrySet()) {
//			allClients.add(c);
//		}
//		return null;
	}

	@Override
	public Client addClient(Client m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client updateClient(Client changeClient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client deleteClient(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
