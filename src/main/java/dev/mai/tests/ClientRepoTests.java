package dev.mai.tests;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dev.mai.models.Account;
import dev.mai.models.Client;
import dev.mai.repositories.ClientRepoDBImpl;


public class ClientRepoTests {

	static ClientRepoDBImpl cr;
	static Scanner sc = new Scanner(System.in);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Run once before running tests.");
		System.out.println("Enter username");
	    String username = sc.nextLine();
	    System.out.println("Enter password");
	    String password = sc.nextLine();

		cr = new ClientRepoDBImpl(username, password);
		
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Runs before every sigle test. ");
	}

	@Test
	public void testGetClientWithId() {
		int id = 1;
		Client c = cr.getClient(id);
		assertEquals("Testing getting client with valid id", id, c.getId());
		
		id = -1;
		c = cr.getClient(id);
		assertNull(c);
	}
	
	@Test 
	public void testGetAllClients() {
		List<Client> clients= cr.getAllClients();
		assertNotNull(clients);
		
	}
	
	@Test
	public void testAddClient() {
		Client c = new Client("Jon", "Doe");
		c = cr.addClient(c);
		assertNotEquals("Id is not equal to Java's default of 0", 0, c.getId());
		
	}
	
	@Test 
	public void testUpdateClient() {
		Client c = cr.getClient(1);
		c.setFirstName("ChangedFirstName");
		Client updatedClient = cr.updateClient(c);
		assertEquals("Client first name changed to ChangedFirstName", "ChangedFirstName", updatedClient.getFirstName()); 
	}
	
	@Test 
	public void testDeleteClient() {
		int id = 6;
		Client c = cr.getClient(id);
		List<Client> clients = cr.getAllClients();
		int clientSize = clients.size();
		
		if (c != null) {
			c = cr.deleteClient(6);
			assertNotEquals(clientSize, cr.getAllClients().size());
		}	
	}
	
	@Test 
	public void testGetAccount() {
		int id = 1;
		Account a = cr.getAccount(id);
		
		assertNotNull(a);
	}
	
	@Test
	public void testAddAccount() {
		Client c = cr.getClient(1);
		Account a = cr.addAccount(c);
		assertNotNull(a);
		
		Client c2 = cr.getClient(-1);
		Account a2 = cr.addAccount(c2);
		assertNull(a2);
	}
	
	@Test 
	public void testUpdateAccount() {
		Account a = cr.getAccount(1);
		double originalBalance = a.getBalance();
		a.setBalance(originalBalance + 100);
		
		a = cr.updateAccount(a);
		assertNotEquals("Original balance is no more", originalBalance, a.getBalance());
	}
	
	@Test 
	public void testDeleteAccount() {
		int id = 16;
		Account a = cr.getAccount(id);
		if (a != null) {
			assertNotNull(a);
			cr.deleteAccount(id);
			a = cr.getAccount(id);
			assertNull(a);
		}
	}
	
	@Test 
	public void testGetAllAccountsFromClient() {
		int id = 1;
		List<Account> accs = cr.getAllAccountsFromClient(id);
		
		assertNotEquals("Client 1 has at least 1 account", 0, accs.size());
	}
	
	@After
	public void afterATest() {
		System.out.println("This runs after every single test");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Tearing down test cases. Runs once");
		cr = null;
	}
}
