package dev.mai.util;

import java.util.HashMap;
import java.util.Map;

import dev.mai.models.Client;

public class FakeDB {
	public static Map<Integer, Client> clients = new HashMap<Integer, Client>();
	public static int idCount = 1;
	
	static {
		Client client1 = new Client(idCount++, "First", "Last");
		Client client2 = new Client(idCount++, "First1", "Last1");
		Client client3 = new Client(idCount++, "First2", "Last2");
		
		clients.put(idCount++, client1);
		clients.put(idCount++, client2);
		clients.put(idCount++, client3);

		
	}

}
