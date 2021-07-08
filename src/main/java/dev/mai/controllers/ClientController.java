package dev.mai.controllers;

import java.util.List;

import com.google.gson.Gson;

import dev.mai.models.Client;
import dev.mai.services.ClientService;
import io.javalin.http.Handler;

public class ClientController {
	private ClientService cs;
	Gson gson = new Gson();

	public ClientController(ClientService cs) {
		this.cs = cs;
	}
	
	public Handler getAllClients = (ctx) -> {
		List<Client> clients = cs.getAllClients();
		
		ctx.result(gson.toJson(clients));
		ctx.status(200);
	};
	
	
	public Handler getClientById = (ctx) -> {
		String input = ctx.pathParam("clientID");
		int id;
		
		if (input.matches("^-?[0-9]+")) { // Regex
			id = Integer.parseInt(input);
			
		} else {
			id = -1;
		}
		
		
		Client client = cs.getClient(id);
		
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
		Client c = gson.fromJson(body, Client.class);
		c = cs.addClient(c);
		ctx.status(201);
		
		ctx.result((c != null) ? gson.toJson(c) : "did not add to db");

	};
	
	public Handler updateClient = (ctx) -> {
		Client client = gson.fromJson(ctx.body(), Client.class);

		client = cs.updateClient(client);
		
		if (client == null) {
			ctx.status(404);
			return;
		}
		
		ctx.result((client != null) ? gson.toJson(client) : "{}");
	};
	
	public Handler deleteClient = (ctx) -> {
		String input = ctx.pathParam("clientID");
		int id;
		
		if (input.matches("^-?[0-9]+")) {
			id = Integer.parseInt(input);
			
		} else {
			id = -1;
		}
		
		Client client = cs.deleteClient(id);
		
		if (client != null ) {
			ctx.result(gson.toJson(client));
			ctx.status(205);
		} else {
			ctx.status(404);
		}
//		context.result((client != null) ? gson.toJson(m) : "{}");

	};
}
