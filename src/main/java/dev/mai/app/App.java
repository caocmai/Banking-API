package dev.mai.app;

import java.util.Scanner;

import dev.mai.controllers.ClientController;
import dev.mai.repositories.ClientRepo;
import dev.mai.repositories.ClientRepoDBImpl;
import dev.mai.services.ClientService;
import dev.mai.services.ClientServiceImpl;
import io.javalin.Javalin;

public class App {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// Javalin object
		Javalin app = Javalin.create();

		// Establish Routes/Endpoints
		establishRoutes(app);

		// Run Javalin
		app.start(); // Default port is 7000, if don't specified

	}

	private static void establishRoutes(Javalin app) {
//		System.out.println("Enter AWS your root link to RDS");
//		String link = sc.nextLine();
//	
//		String endpoint = "caorevaturedb.c39sfp4pzzjr.us-east-2.rds.amazonaws.com";

		System.out.println("Enter(Paste) your Amazon Relational Database Service (RDS) with PostgreSQL endpoint: ");
		String endpoint = sc.nextLine();
		System.out.println("Enter your PostgreSQL username: ");
		String username = sc.nextLine();
		System.out.println("Enter your PostgresSQL password: ");
		String password = sc.nextLine();

		ClientRepoDBImpl cr = new ClientRepoDBImpl(endpoint, username, password);
		ClientService cs = new ClientServiceImpl(cr);
		ClientController cc = new ClientController(cs);

		// List of routes (endpoints) for javalin
		app.get("/", (ctx) -> ctx.result("Welcome to the Banking API!"));
		app.get("/clients/:clientID", cc.getClientById);
		app.get("/clients", cc.getAllClients);
		app.post("/clients", cc.addClient);
		app.put("/clients/:clientID", cc.updateClient);
		app.delete("/clients/:clientID", cc.deleteClient);

		app.post("/clients/:clientID/accounts", cc.createAccount);
		app.get("/clients/:clientID/accounts", cc.getAccountsFromClient);
		app.get("/clients/:clientID/accounts/:accountID", cc.getAnAccount);
		app.delete("/clients/:clientID/accounts/:accountID", cc.deleteAnAccount);
		app.put("/clients/:clientID/accounts/:accountID", cc.updateAccount);

		app.patch("/clients/:clientID/accounts/:accountID", cc.balanceManipulation);
		app.patch("/clients/:clientID/accounts/:accountIDFrom/transfer/:accountIDTo", cc.transferBalance);

	}
}
