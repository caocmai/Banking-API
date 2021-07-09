package dev.mai.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.mai.models.Account;
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

	@Override
	public Account getAccount(int id) {
		String sql = "SELECT * FROM accounts WHERE a_id = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Account addAccount(Client c) {
		if (c == null) 
			return null;

		String sql = "INSERT INTO accounts VALUES (default,?) RETURNING *";
		Account acc = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			// set the default balance to 0, can make this default in db
			ps.setInt(1, 0);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				acc = buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// set the relationship between account and client
		String sql2 = "INSERT INTO client_accounts VALUES (?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql2);
			ps.setInt(1, c.getId());
			ps.setInt(2, acc.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return acc;
	}

	@Override
	public Account updateAccount(Account changeAccount) {
		String sql = "UPDATE accounts SET balance=? WHERE a_id=? RETURNING *";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, changeAccount.getBalance());
			ps.setInt(2, changeAccount.getId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Account deleteAccount(int id) {
		String sql = "DELETE FROM accounts WHERE a_id=? RETURNING *";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return buildAccount(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Account> getAllAccountsFromClient(int id) {
		String sql = "SELECT account_id FROM client_accounts WHERE client_id=?";
		List<Account> accounts = new ArrayList<Account>();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account a = getAccount(rs.getInt("account_id"));
				if (a != null) {
					accounts.add(a);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	// helper method
	private Client buildClient(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setId(rs.getInt("c_id"));
		client.setFirstName(rs.getString("first_name"));
		client.setLastName(rs.getString("last_name"));

		return client;
	}

	// helper method
	private Account buildAccount(ResultSet rs) throws SQLException {
		Account acc = new Account();
		acc.setId(rs.getInt("a_id"));
		acc.setBalance(rs.getDouble("balance"));

		return acc;
	}

}
