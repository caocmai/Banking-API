package dev.mai.controllers;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;

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
		log.info("Info: Got all Clients");
		ctx.result(gson.toJson(clients));
		ctx.status(200);
	};
	
	
	public Handler getClientById = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		int id = checkInt(clientId);
		
		Client client = cs.getClient(id);
		log.info("Info: Got a single client with id: " + clientId);
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
		log.info("Info: Update a single client with id: " + client.getId());

		ctx.result((client != null) ? gson.toJson(client) : "{}");
	};
	
	public Handler deleteClient = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		int id = checkInt(clientId);
		
		Client client = cs.deleteClient(id);
		
		if (client != null ) {
			ctx.result(gson.toJson(client));
			ctx.status(204);
			log.info("Info: Delete a single client with id: " + client.getId());

		} else {
			ctx.status(404);
			log.debug("Debug: Update a single client with id: " + id + "failed");

		}
//		context.result((client != null) ? gson.toJson(m) : "{}");

	};
	public Handler createAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		
		int id = checkInt(clientId);
		
		Account acc = cs.addAccountToClient(id);
		if (acc == null) {
			ctx.status(404);
			log.debug("Debug: Create a single client with id: " + id + " failed");
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
			log.debug("Debug: Account does not exist to delete.");
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
		Client c = cs.getClient(cID);
		Account a = cs.getAnAccount(cID, aID);
		if (a == null || c == null) {
			ctx.status(404);
			return;
		}
		
		CommandJson cj = gson.fromJson(ctx.body(), CommandJson.class);
		if (cj == null ) {
			ctx.status(404);
			return;
		}
		
		String command = cj.getCommand();
		Double amount = cj.getAmount();
		
		if (command.equals("deposit")) {
			a.setBalance(a.getBalance()+amount);
			a = cs.updateAccount(c.getId(), a);
			ctx.status(200);
			ctx.result(gson.toJson(a));
			return;
		} else if (command.equals("withdraw")) {
			if (a.getBalance() < amount) {
				log.info("Info: Withdraw can't complete because insufficient funds");
				ctx.status(422);
				return;
			} else {
				a.setBalance(a.getBalance()-amount);
				a = cs.updateAccount(c.getId(), a);
				ctx.status(200);
				ctx.result(gson.toJson(a));
			}
		} else {
			ctx.status(404);
			log.info("Info: Command not valid for this route!");
		}

	};

	public Handler transferBalance = (ctx) -> {
		String clientId = ctx.pathParam("clientID");
		String fromAccount = ctx.pathParam("accountIDFrom");
		String toAccount = ctx.pathParam("accountIDTo");
		
		CommandJson cj = gson.fromJson(ctx.body(), CommandJson.class);
		if (cj == null ) {
			ctx.status(404);
			return;
		}
		String command = cj.getCommand();
		Double amount = cj.getAmount();
		
		int cID = checkInt(clientId);
		int fID = checkInt(fromAccount);
		int tID	= checkInt(toAccount);
		
		Account fromAcc = cs.getAnAccount(cID, fID);
		Account endAcc = cs.getAnAccount(tID);
		
		if (fromAcc == null || endAcc == null) {
			ctx.status(404);
			return;
		}
		
		if (command.equals("transfer")) {
			if (fromAcc.getBalance() < amount) {
				ctx.status(422);
				log.info("Info: Insufficient funds to transfer");
				return;
			}
			
			fromAcc.setBalance(fromAcc.getBalance()-amount);
			endAcc.setBalance(endAcc.getBalance()+amount);
			
			cs.updateAccount(fromAcc.getId(), fromAcc);
			cs.updateAccount(endAcc);
			ctx.status(200);
			
			ctx.result(gson.toJson(endAcc));
			
		} else {
			ctx.status(404);
			log.info("Info: Command not valid for this route!");
		}
		
		
	};

	private int checkInt(String input) {
		if (input.matches("^-?[0-9]+")) {
			return Integer.parseInt(input);
		} else {
			return -1;
		}
		
	}
}
