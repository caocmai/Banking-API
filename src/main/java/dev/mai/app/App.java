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
		 
		ClientRepo cr = new ClientRepoDBImpl();
		ClientService cs = new ClientServiceImpl(cr);
		ClientController cc = new ClientController(cs);
		
		
		app.get("/", (ctx) -> ctx.result("Welcome to the Banking API!"));
		app.get("/client/:clientId", cc.getClientById);

	}
}
