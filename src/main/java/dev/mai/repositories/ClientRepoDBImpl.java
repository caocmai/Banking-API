package dev.mai.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dev.mai.models.Client;
import dev.mai.util.JDBCConnection;

public class ClientRepoDBImpl implements ClientRepo {
	
	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public Client getClient(int id) {
		System.out.println("goes outside");

		String sql = "SELECT * FROM clients WHERE c_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			System.out.println("IS it here???8768768768");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return buildClient(rs);
			}
		} catch (SQLException e) {
			System.out.println("IS it here???");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Client> getAllClients() {
		return null;
	}

	@Override
	public Client addClient(Client m) {
		return null;
	}

	@Override
	public Client updateClient(Client changeClient) {
		return null;
	}

	@Override
	public Client deleteClient(int id) {
		return null;
	}
	
	private Client buildClient(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setId(rs.getInt("c_id"));
		client.setFirstName("first_name");
		client.setLastName("last_name");
		
		return client;
	}
	
}
