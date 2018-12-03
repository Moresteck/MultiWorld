package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MPluginInfo extends MCommand {

	public MPluginInfo(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.perm = "none";
	}

	public void execute() {
		boolean version = this.exists(0, "version");
		if (!version) {
			return;
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
		}
		this.send(ChatColor.AQUA + "============" + ChatColor.GOLD + " MULTIWORLD PLUGIN " + ChatColor.AQUA + "============");
		this.send("This plugin was made possible by " + ChatColor.GREEN + "BetaCraft server");
		this.send("Author: " + ChatColor.GREEN + "Moresteck");
		this.send("General help: " + ChatColor.AQUA + "Kazu (KazuGod)");
		this.send("Website: " + ChatColor.GRAY + "https://" + ChatColor.YELLOW + "betacraft" + ChatColor.GRAY + "." + ChatColor.YELLOW + "ovh" + ChatColor.GRAY + "/");
		this.send(" Thanks for using this plugin! I hope you find it handy :)");
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw version");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Displays some information about the plugin");
	}
}
