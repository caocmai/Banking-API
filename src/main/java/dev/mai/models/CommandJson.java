package dev.mai.models;

public class CommandJson {
	private String command;
	private Double amount;

	public CommandJson() {
		super();
	}
	
	public CommandJson(String command, Double amount) {
		super();
		this.command = command;
		this.amount = amount;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
