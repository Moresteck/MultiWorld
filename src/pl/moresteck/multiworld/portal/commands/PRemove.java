package pl.moresteck.multiworld.portal.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.commands.MCommand;
import pl.moresteck.multiworld.portal.Portal;

public class PRemove extends MCommand {

	public PRemove(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.portal.remove");
	}

	public void execute() {
		if (!this.getCommand().isMWP()) return;
		if (!this.exists(0, "remove")) return;

		int arg = this.getArgs().length;
		if (arg == 1) {
			this.displayCommandHelp();
			return;
		}
		if (args[1].equalsIgnoreCase("help")) {
			this.displayCommandHelp();
			return;
		}
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}
		String name = args[1];
		if (!Portal.portalExists(name)) {
			this.send("A portal with this name isn't defined!");
			return;
		}
		boolean portal = new Portal(name).removePortal();
		if (!portal) {
			this.send("Something went wrong... But it shouldn't!");
			return;
		}
		this.send("Removed portal '" + name + "'");
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mwp remove " + ChatColor.GRAY + "<name>");
		this.send(ChatColor.DARK_GRAY + " name" + ChatColor.WHITE + " - Portal's name");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Removes a portal");
	}
}
