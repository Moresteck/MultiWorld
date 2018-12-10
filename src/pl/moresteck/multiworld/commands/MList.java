package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;

public class MList extends MCommand {

	public MList(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.info.list");
	}

	public void execute() {
		boolean list = this.exists(0, "list");
		if (!list) {
			return;
		}
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

		this.send("World list:");
		for (MWorld world : MultiWorld.worlds) {
			this.send(" - " + getColor(world) + world.getName());
		}
		this.send("To view more details about each world, type: " + ChatColor.YELLOW + "/mw info <name>");
	}

	public static ChatColor getColor(MWorld world) {
		Environment env = world.getEnvironment();
		if (env == Environment.NORMAL) return ChatColor.GREEN;
		if (env == Environment.NETHER) return ChatColor.RED;
		else return ChatColor.AQUA;
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw list");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Views the world list");
	}
}
