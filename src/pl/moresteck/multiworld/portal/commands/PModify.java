package pl.moresteck.multiworld.portal.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.commands.MCommand;
import pl.moresteck.multiworld.portal.Portal;

public class PModify extends MCommand {

	public PModify(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.portal.modify");
	}

	public void execute() {
		if (!this.getCommand().isMWP()) return;
		if (!this.exists(0, "modify")) return;

		int arg = this.getArgs().length;
		if (arg == 1 || arg == 2) {
			this.displayCommandHelp();
			return;
		}
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}
		Portal portal = null;
		if (!Portal.portalExists(args[1])) {
			this.send("A portal with this name isn't defined!");
			return;
		}
		portal = new Portal(args[1]);
		String node = args[2];
		if (arg == 3) {
			boolean is = false;
			if (node.equalsIgnoreCase("fancy-cooldown")) {
				is = portal.getFancyCooldown();
				this.send("fancy-cooldown: " + (is ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
			}
			return;
		}
		boolean set;
		if (args[3].equalsIgnoreCase("true")) {
			set = true;
		} else {
			set = false;
		}
		if (node.equalsIgnoreCase("fancy-cooldown")) {
			portal.setFancyCooldown(set);
		} else {
			this.send("No entry with this name.");
			return;
		}
		this.send("Set '" + node + "' to " + (set ? ChatColor.GREEN + "true" : ChatColor.RED + "false"));
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mwp modify " + ChatColor.GRAY + "<name> " + ChatColor.BLUE + "<property> " + ChatColor.GRAY + "<set_value>");
		this.send(ChatColor.DARK_GRAY + " name" + ChatColor.WHITE + " - Portal's name");
		this.send(ChatColor.DARK_GRAY + " property" + ChatColor.WHITE + " - Property you wish to change");
		this.send(ChatColor.DARK_GRAY + " set_value" + ChatColor.WHITE + " - To what value set the property");
		this.send("Properties available: " + ChatColor.GREEN + "fancy-cooldown");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Modifies portal's config property");
	}
}
