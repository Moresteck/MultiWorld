package pl.moresteck.multiworld.portal.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.commands.MCommand;
import pl.moresteck.multiworld.portal.Portal;

public class PList extends MCommand {

	public PList(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.portal.list");
	}

	public void execute() {
		if (!this.getCommand().isMWP()) return;
		boolean list = this.exists(0, "list");
		if (!list) return;
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
		}
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}

		this.send("Portal list (" + Portal.getPortals().size() + "):");
		for (Portal portal : Portal.getPortals()) {
			this.send(" - " + portal.getName());
		}
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mwp list");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Views the portal list");
	}
}
