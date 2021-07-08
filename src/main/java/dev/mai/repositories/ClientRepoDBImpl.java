package dev.mai.repositories;

import java.sql.Connection;
import java.util.List;

import dev.mai.models.Client;
import dev.mai.util.JDBCConnection;

public class ClientRepoDBImpl implements ClientRepo {
	
	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public Client getClient(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> getAllClients() {
		// TODO Auto-generated method stub
		return null;
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
