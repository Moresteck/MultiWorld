package pl.moresteck.multiworld.portal.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.commands.MCommand;

public class PHelp extends MCommand {

	public PHelp(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
	}

	public void execute() {
		if (!this.getCommand().isMWP()) return;
		if (this.exists(0, "help") && args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
		}
		boolean help = this.args.length == 0 ? true : this.exists(0, "help") && this.args.length == 1;
		if (help || this.exists(1, "1")) {
			this.send(ChatColor.YELLOW + "==============" + ChatColor.GOLD + " MultiWorld " + ChatColor.YELLOW + "==============");
			this.send(ChatColor.AQUA + "---- Help Page 1 ----");
			this.send(ChatColor.BLUE + "/mwp create " + ChatColor.GRAY + "<portal> " + ChatColor.BLUE + "[cooldown]");
			this.send(ChatColor.BLUE + "/mwp remove " + ChatColor.GRAY + "<portal>");
			this.send(ChatColor.BLUE + "/mwp list");
			this.send(ChatColor.BLUE + "/mwp destination " + ChatColor.GRAY + "<portal> " + ChatColor.BLUE + "[destination]");
			this.send(ChatColor.BLUE + "/mwp modify " + ChatColor.GRAY + "<portal> " + ChatColor.BLUE + "<property> " + ChatColor.GRAY + "[set_value]");
			this.send(ChatColor.AQUA + "------------------------------");
			this.send(ChatColor.BLUE + "/mwp " + ChatColor.GRAY + "<command>" + ChatColor.BLUE + " help " + ChatColor.WHITE + "- Shows help for each command");
		}
		if (this.exists(1, "2")) {
			this.send(ChatColor.YELLOW + "==============" + ChatColor.GOLD + " MultiWorld " + ChatColor.YELLOW + "==============");
			this.send(ChatColor.AQUA + "---- Help Page 2 ----");
			this.send(ChatColor.AQUA + "---- Previous Page: /mw help ----");
			this.send(ChatColor.BLUE + "/mwp " + ChatColor.GRAY + "<command>" + ChatColor.BLUE + " help " + ChatColor.WHITE + "- Shows help for each command");
		}
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mwp help");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + ChatColor.GREEN + "everyone");
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Displays command list to the sender");
	}
}
