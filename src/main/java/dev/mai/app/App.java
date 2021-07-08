package dev.mai.app;

import dev.mai.controllers.ClientController;
import dev.mai.repositories.ClientRepo;
import dev.mai.repositories.ClientRepoDBImpl;
import dev.mai.services.ClientService;
import dev.mai.services.ClientServiceImpl;
import io.javalin.Javalin;

public class App {


	public static void main(String[] args) {

		// Javalin object
		Javalin app = Javalin.create();
		
		// Establish Routes/Endpoints
		establishRoutes(app);
		
		// Run Javalin
		app.start(); // Default port is 7000, if don't specified
		
	}

	private static void establishRoutes(Javalin app) {
		// List of routes (endpoints) for javalin
		 
		ClientRepoDBImpl cr = new ClientRepoDBImpl();
		ClientService cs = new ClientServiceImpl(cr);
		ClientController cc = new ClientController(cs);
		
		
		app.get("/", (ctx) -> ctx.result("Welcome to the Banking API!"));
		app.get("/clients/:clientID", cc.getClientById);
		app.get("/clients", cc.getAllClients);
		app.post("/clients", cc.addClient);
		app.put("/clients/:clientID", cc.updateClient);
		app.delete("/clients/:clientID", cc.deleteClient);
		
		app.post("/clients/:clientID/accounts", cc.createAccount);
		app.get("/clients/:clientID/accounts", cc.getAccountsFromClient);
		app.get("/clients/:clientID/accounts/:accountID", cc.getAnAccount);
		
//		app.get("/clients/:clientID/accounts?", cc.getFilteredAccounts);
		
		app.delete("/clients/:clientID/accounts/:accountID", cc.deleteAnAccount);
		app.put("/clients/:clientID/accounts/:accountID", cc.updateAccount);
		
		app.patch("/clients/:clientID/accounts/:accountID", cc.balanceManipulation);
		app.patch("/clients/:clientID/accounts/:accountIDFrom/transfer/:accountIDTo", cc.transferBalance);


	}
}
