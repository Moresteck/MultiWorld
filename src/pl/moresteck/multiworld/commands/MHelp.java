package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.bvnpe.BukkitVersion;

public class MHelp extends MCommand {

	public MHelp(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
	}

	public void execute() {
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
			// b1.6.6+
			if (BukkitVersion.getVersionId() >= 9) {
				this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[seed]" + ChatColor.BLUE + " [generator]");
				this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[generator]");
			// b1.4 - b1.6.5
			} else if (BukkitVersion.getVersionId() >= 3) {
				this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[seed]");
				this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
			// b1.3-
			} else {
				this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
				this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
			}
			this.send(ChatColor.BLUE + "/mw unload " + ChatColor.GRAY + "<world_name>");
			this.send(ChatColor.BLUE + "/mw info " + ChatColor.GRAY + "[world_name] [page]");
			this.send(ChatColor.BLUE + "/mw list");
			this.send(ChatColor.AQUA + "---- Next Page: /mw help 2 ----");
			this.send(ChatColor.BLUE + "/mw " + ChatColor.GRAY + "<command>" + ChatColor.BLUE + " help " + ChatColor.WHITE + "- Shows help for each command");
		}
		if (this.exists(1, "2")) {
			this.send(ChatColor.YELLOW + "==============" + ChatColor.GOLD + " MultiWorld " + ChatColor.YELLOW + "==============");
			this.send(ChatColor.AQUA + "---- Help Page 2 ----");
			this.send(ChatColor.BLUE + "/mw tp " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " [player]");
			this.send(ChatColor.BLUE + "/mw setspawn");
			this.send(ChatColor.BLUE + "/mw who " + ChatColor.GRAY + "[world_name]");
			this.send(ChatColor.BLUE + "/mw save " + ChatColor.GRAY + "[world_name]");
			this.send(ChatColor.BLUE + "/mw version");
			this.send(ChatColor.AQUA + "---- Previous Page: /mw help ----");
			this.send(ChatColor.BLUE + "/mw " + ChatColor.GRAY + "<command>" + ChatColor.BLUE + " help " + ChatColor.WHITE + "- Shows help for each command");
		}
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw help " + ChatColor.GRAY + "[page]");
		this.send(ChatColor.DARK_GRAY + " page" + ChatColor.WHITE + " - Help page");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + ChatColor.GREEN + "everyone");
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Displays command list to the sender");
	}
}
