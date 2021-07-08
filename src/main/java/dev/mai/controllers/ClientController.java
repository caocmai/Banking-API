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
	
	public Handler getAllClients = (context) -> {
		List<Client> clients = cs.getAllClients();
		
		context.result(gson.toJson(clients));
	};
	
	
	public Handler getClientById = (ctx) -> {
		String input = ctx.pathParam("clientId");
		int id;
		
		if (input.matches("^-?[0-9]+")) { // Regex
			id = Integer.parseInt(input);
			
		} else {
			id = -1;
		}
		
		
		Client client = cs.getClient(id);
		
		if (client != null ) {
			ctx.result(gson.toJson(client));
		} else {
			ctx.status(400);
		}
		
		ctx.result((client != null) ? gson.toJson(client) : "{}");

	};
}
