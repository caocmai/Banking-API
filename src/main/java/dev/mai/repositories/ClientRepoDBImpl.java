package dev.mai.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.mai.models.Client;
import dev.mai.util.JDBCConnection;

public class ClientRepoDBImpl implements ClientRepo, AccountRepo {

	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public Client getClient(int id) {

		String sql = "SELECT * FROM clients WHERE c_id = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return buildClient(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Client> getAllClients() {
		String sql = "SELECT * FROM clients";
		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<Client> clients = new ArrayList<Client>();

			while (rs.next()) {
				Client m = buildClient(rs);
				clients.add(m);
			}

			return clients;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Client addClient(Client c) {
		String sql = "INSERT INTO clients VALUES (default,?,?) RETURNING *";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, c.getFirstName());
			ps.setString(2, c.getLastName());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildClient(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Client updateClient(Client changeClient) {

		String sql = "UPDATE clients SET first_name=?, last_name=? WHERE c_id=? RETURNING *";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, changeClient.getFirstName());
			ps.setString(2, changeClient.getLastName());
			ps.setInt(3, changeClient.getId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildClient(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Client deleteClient(int id) {
		String sql = "DELETE FROM clients WHERE c_id=? RETURNING *";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildClient(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// helper method
	private Client buildClient(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setId(rs.getInt("c_id"));
		client.setFirstName(rs.getString("first_name"));
		client.setLastName(rs.getString("last_name"));

		return client;
	}

}
