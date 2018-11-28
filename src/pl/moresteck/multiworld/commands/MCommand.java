package pl.moresteck.multiworld.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MCommand {
	private Command cmd;
	private CommandSender cs;
	protected String[] args;
	protected String perm;

	public MCommand(Command cmd, CommandSender cs, String[] args) {
		this.cmd = cmd;
		this.cs = cs;
		this.args = args;
		this.perm = "multiworld.*";
	}

	public CommandSender getSender() {
		return this.cs;
	}

	public String getCommand() {
		return this.cmd.getName();
	}

	public String[] getArgs() {
		return this.args;
	}

	public void send(String message) {
		if (this.getSender() instanceof Player) {
			this.getSender().sendMessage(message);
		} else {
			this.getSender().sendMessage(" [MultiWorld] " + message);
		}
	}

	public boolean exists(int index, String str) {
		boolean help;
		try {
			help = this.args[index].equalsIgnoreCase(str);
		} catch (Exception ex) {
			help = false;
		}
		return help;
	}

	public abstract void execute();

	public abstract void displayCommandHelp();
}
