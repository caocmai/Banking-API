package dev.mai.tests;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dev.mai.repositories.ClientRepoDBImpl;


public class ClientRepoTests {

	static ClientRepoDBImpl cr;
	
	@BeforeClass
	public static void setUPBeforeClass() throws Exception {
		System.out.println("Run once before running tests.");
		cr = new ClientRepoDBImpl();
		
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Runs before every sigle test. ");
	}

// this is where tests go
//	@Test
//	public void testadd() {
//		assertEquals("Testing 2 +2 = 4",4, calc.add(2, 2));
//	}

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
