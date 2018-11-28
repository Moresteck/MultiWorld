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
		boolean help = this.args.length == 0 ? true : this.exists(0, "help") && this.args.length == 1;
		if (help || this.exists(1, "1")) {
			this.send(ChatColor.YELLOW + "==============" + ChatColor.GOLD + " MultiWorld " + ChatColor.YELLOW + "==============");
			this.send(ChatColor.AQUA + "---- Help Page 1 ----");
			this.send(ChatColor.RED + "Keep in mind that some commands haven't been introduced yet!");
			// b1.6.6+
			if (BukkitVersion.getVersionId() >= 9) {
				this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[seed]" + ChatColor.BLUE + " [generator]");
				this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[generator]");
			// b1.4 - b1.6.5
			} else if (BukkitVersion.getVersionId() >= 3) {
				this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[seed]");
			// b1.3-
			} else {
				this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
			}
			this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
			this.send(ChatColor.BLUE + "/mw remove " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " [-files]");
			this.send(ChatColor.BLUE + "/mw info " + ChatColor.GRAY + "<world_name>");
			this.send(ChatColor.BLUE + "/mw list");
			this.send(ChatColor.AQUA + "---- Next Page: /mw help 2 ----");
		}
		if (this.args.length != 2) {
			return;
		}
		if (this.exists(1, "2")) {
			this.send(ChatColor.YELLOW + "==============" + ChatColor.GOLD + " MultiWorld " + ChatColor.YELLOW + "==============");
			this.send(ChatColor.BLUE + "/mw tp " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " [player]");
			this.send(ChatColor.BLUE + "/mw setspawn");
			this.send(ChatColor.AQUA + "---- Help Page 2 ----");
		}
	}

	public void displayCommandHelp() {
		this.execute();
	}
}
