package dev.mai.controllers;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

//import dev.mai.app.App;
import dev.mai.models.Account;
import dev.mai.models.Client;
import dev.mai.models.CommandJson;
import dev.mai.services.ClientService;
import io.javalin.http.Handler;

public class ClientController {
	final static Logger log = Logger.getLogger(ClientController.class);

	private ClientService cs;
	Gson gson = new Gson();

	public ClientController(ClientService cs) {
		this.cs = cs;
	}
	
	public Handler getAllClients = (ctx) -> {
		List<Client> clients = cs.getAllClients();
		log.info("info: Got all Clients");
		ctx.result(gson.toJson(clients));
		ctx.status(200);
	};
	
	
	public Handler getClientById = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		int id = checkInt(clientId);
		
		Client client = cs.getClient(id);
		log.info("info: Got a single client with id: " + clientId);
		if (client != null ) {
			ctx.result(gson.toJson(client));
			ctx.status(200);
		} else {
			ctx.status(404);
		}
		
		ctx.result((client != null) ? gson.toJson(client) : "{}");

	};
	
	public Handler addClient = (ctx) -> {
		String body = ctx.body();
		Client client = gson.fromJson(body, Client.class);
		log.info("info: Add a single client with id: " + client.getId());

		client = cs.addClient(client);
		ctx.status(201);
		
		ctx.result((client != null) ? gson.toJson(client) : "did not add to db");

	};
	
	public Handler updateClient = (ctx) -> {
		Client client = gson.fromJson(ctx.body(), Client.class);

		client = cs.updateClient(client);
		
		if (client == null) {
			ctx.status(404);
			return;
		}
		log.info("info: Update a single client with id: " + client.getId());

		ctx.result((client != null) ? gson.toJson(client) : "{}");
	};
	
	public Handler deleteClient = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		int id = checkInt(clientId);
		
		Client client = cs.deleteClient(id);
		
		if (client != null ) {
			ctx.result(gson.toJson(client));
			ctx.status(204);
			log.info("info: Delete a single client with id: " + client.getId());

		} else {
			ctx.status(404);
			log.debug("Debug: Update a single client with id: " + id + "failed");

		}
//		context.result((client != null) ? gson.toJson(m) : "{}");

	};
	public Handler createAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		
		int id = checkInt(clientId);
		
//		Client c = cs.getClient(id);
//		
//		if (c == null) {
//			ctx.status(404);
//			return;
//		}
		
		Account acc = cs.addAccountToClient(id);
		if (acc == null) {
			ctx.status(404);
			log.debug("Debug: Create a single client with id: " + id + "failed");
			return;
		};
	
		ctx.status(201);
		
		ctx.result((acc != null) ? gson.toJson(acc) : "did not add to db");
	};
	
	public Handler getAccountsFromClient = (ctx) -> {
		
		String clientId = ctx.pathParam("clientID");
		int id = checkInt(clientId);
		
		double min = ctx.queryParam("amountGreaterThan", Double.class, "0").get();
		double max = ctx.queryParam("amountLessThan", Double.class, String.valueOf(Double.MAX_VALUE)).get();	

		List<Account> accounts;
		
		if (min == 0) {
			accounts = cs.getAllAccountsFromClient(id);
		} else {
			accounts = cs.getAccountsBetween(id, min, max);
			log.info("Info: Showing accounts between $" + min + " and $" + max);
		}
		
		// if accounts is null client doesn't exists or client exists but no accounts
		if (accounts == null || accounts.size() == 0) {
			ctx.status(404);
			return;
		}
		
		ctx.result(gson.toJson(accounts));
		ctx.status(200);
	};

	public Handler getAnAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		String accountId = ctx.pathParam("accountID");
		
		int cID = checkInt(clientId);
		int aID = checkInt(accountId);
		
		Account a = cs.getAnAccount(cID, aID);
		
		if (a == null) {
			ctx.status(404);
			return;
		}

		ctx.result(gson.toJson(a));
		ctx.status(200);

	};

	public Handler deleteAnAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		String accountId = ctx.pathParam("accountID");
		
		int cID = checkInt(clientId);
		int aID = checkInt(accountId);
		
		Account a = cs.deleteAccount(cID, aID);
		
		if (a == null) {
			log.debug("Debug: Accounts does not exist to delete.");
			ctx.status(404);
			return;
		}
		ctx.result(gson.toJson(a));
		ctx.status(204);
		
	};

	public Handler updateAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		Account account = gson.fromJson(ctx.body(), Account.class);
		
		int cID = checkInt(clientId);
		
		Account a = cs.updateAccount(cID, account);
		
		if (a == null) {
			log.debug("Debug: Can't update account because either client or account doesn't exist");
			ctx.status(404);
			return;
		} 
		ctx.result(gson.toJson(a));
		ctx.status(200);
		
	};

	public Handler balanceManipulation = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		String accountId = ctx.pathParam("accountID");
		
		int cID = checkInt(clientId);
		int aID = checkInt(accountId);
		
//		CommandJson cj = gson.fromJson(ctx.body(), CommandJson.class);

	};

//	public Handler getFilteredAccounts = (ctx) -> {
//
//		String clientId = ctx.pathParam("clientID");
//		int id = checkInt(clientId);
//		int min = ctx.queryParam("amountGreaterThan", Integer.class).get();
//		int max = ctx.queryParam("amountLessThan", Integer.class).get();
//		
//		List<Account> filteredAccs = cs.getAccountsBetween(id, min, max);
//		if (filteredAccs.size() == 0) {
//			log.debug("Debug: Accounts not found or Client doesn't exist.");
//			ctx.status(404);
//			return;
//		}
//		
//		log.info("Info: Showing accounts between $" + min + " and $" + max);
//
//		ctx.result(gson.toJson(filteredAccs));
//		ctx.status(200);
//	};
//	
	
	private int checkInt(String input) {
		if (input.matches("^-?[0-9]+")) {
			return Integer.parseInt(input);
			
		} else {
			return -1;
		}
		
	}
}
