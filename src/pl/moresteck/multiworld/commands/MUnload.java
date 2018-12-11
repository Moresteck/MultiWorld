package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;

public class MUnload extends MCommand {

	public MUnload(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.world.unload");
	}

	public void execute() {
		boolean unload = this.exists(0, "unload");
		if (!unload) {
			return;
		}
		if (args.length == 1) {
			// mw unload
			this.displayCommandHelp();
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
		// mw unload [world]
		String name = args[1];
		MWorld world = MultiWorld.getWorld(name);
		if (world == null) {
			this.send("This world is not loaded: " + name);
			return;
		}
		String response = MultiWorld.unloadWorld(world);
		this.send(response);
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw unload " + ChatColor.GRAY + "<world_name>");
		this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - World to unload");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Unloads world from the server" + (BukkitVersion.getVersionId() >= 9 ? "" : " (takes effect after server restart)"));
	}
}
