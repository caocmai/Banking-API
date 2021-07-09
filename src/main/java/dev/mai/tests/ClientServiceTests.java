package dev.mai.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dev.mai.models.Account;
import dev.mai.models.Client;
import dev.mai.repositories.ClientRepoDBImpl;
import dev.mai.services.ClientServiceImpl;


public class ClientServiceTests {
	
	static ClientServiceImpl cs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Run once before running tests.");
		ClientRepoDBImpl cr = new ClientRepoDBImpl();
		cs = new ClientServiceImpl(cr);
		
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Runs before every sigle test. ");
	}


	@Test
	public void testGetClient() {
		int id = 1;
		Client c = cs.getClient(id);
		assertEquals("Client has an id of 1", id, c.getId());
	}
	
	@Test 
	public void testGetAllClients() {
		List<Client> clients = cs.getAllClients();
		assertNotNull(clients);
	}
	
	@Test 
	public void testAddClient() {
		Client c = new Client("Joey", "Kangaroo");
		c = cs.addClient(c);
		assertNotEquals("Client has been added to db with an Id not 0", 0, c.getId());
	}
	
	@Test
	public void testUpdateClient() {
		int id = 1;
		Client c = cs.getClient(id);
		c.setLastName("ChangedLastName");
		Client updatedClient = cs.updateClient(c);
		assertEquals("Client last name has been changed", "ChangedLastName", updatedClient.getLastName());
	}
	
	@Test
	public void testDeleteClient() {
		int id = 11;
		Client c = cs.getClient(11);
		int clientSize = cs.getAllClients().size();
		
		if (c != null) {
			cs.deleteClient(id);
			assertNotEquals("Database size should change after delete", clientSize, cs.getAllClients().size());
		}
	}
	
	@Test
	public void testAddAccountToClient() {
		int id = 1;
		Account a = cs.addAccountToClient(id);
		assertNotNull(a);
		
		// adding account to invalid Client
		id = -1;
		a = cs.addAccountToClient(id);
		
		assertNull(a);
	}
	
	@Test
	public void testGetAllAccountsFromClient() {
		int id = 1;
		int accountSize = cs.getAllAccountsFromClient(id).size();
		
		assertNotEquals("Size is not 0", 0, accountSize);
		
		id = -1;
		accountSize = cs.getAllAccountsFromClient(id).size();
		assertEquals("Size is 0 with invalid client", 0, accountSize);
	}
	
	
	@Test
	public void testGetAnAccount() {
		int id = 1;
		Account a = cs.getAnAccount(id);
		assertNotNull(a);
		
	}
	
	@Test
	public void testGetAnAccountWithClientId() {
		int clientId = 1;
		int accountId = 1;
		
		Account a = cs.getAnAccount(clientId, accountId);
		assertNotNull(a);
		
		clientId = 9999999;
		a = cs.getAnAccount(clientId, accountId);
		assertNull(a);
	}
	
	@Test
	public void testDeleteAccount() {
		int clientId = 1;
		int accountId = 18;
		Account a = cs.getAnAccount(accountId);
		
		if (a != null) {
			cs.deleteAccount(clientId, accountId);
			a = cs.getAnAccount(accountId);
			assertNull(a);
		}
	}
	
	@Test
	public void testUpdateAccount() {
		int accountId = 1;
		
		Account a = cs.getAnAccount(accountId);
		double originalBalance = a.getBalance();
		a.setBalance(a.getBalance()+100);
		
		Account updatedAccount = cs.updateAccount(a);
		assertEquals("They should have the same accountId", a.getId(), updatedAccount.getId());
		assertNotEquals("Balance of original and updated accounts are different", originalBalance, updatedAccount.getBalance());
	}
	
	@Test
	public void testUpdateAccountWithClientId() {
		int clientId = 1;
		int accountId = 1;
		Account a = cs.getAnAccount(accountId);
		double originalBalance = a.getBalance();
		a.setBalance(a.getBalance()+100);
		
		a = cs.updateAccount(clientId, a);
		Account updatedAccount = cs.updateAccount(clientId, a);

		assertEquals("They should have the same accountId", a.getId(), updatedAccount.getId());
		assertNotEquals("Balance of original and updated accounts are different", originalBalance, updatedAccount.getBalance());
		
		clientId = -1;
		updatedAccount = cs.updateAccount(clientId, a);
		assertNull(updatedAccount);

	}
	
	@Test
	public void testGetAccountsBetween() {
		int clientId = 1;
		double min = 0.0;
		double max = Double.MAX_VALUE;
		
		List<Account> allAccounts = cs.getAccountsBetween(clientId, min, max);
		int allAcountsSize = allAccounts.size();
		min = 400.0;
		max = 6000.0;
		
		List<Account> filteredAccounts = cs.getAccountsBetween(clientId, min, max);
		int filteredAccountsSize = filteredAccounts.size();

		assertNotEquals("Accounts should not be equal using with provided min and max", allAcountsSize, filteredAccountsSize);
		
	}
	
	@After
	public void afterATest() {
		System.out.println("This runs after every single test");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Tearing down test cases. Runs once");
		cs = null;
	}
	
}
