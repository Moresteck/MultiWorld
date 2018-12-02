package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;
import pl.moresteck.multiworld.world.MWorld;

public class MSave extends MCommand {

	public MSave(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.perm = "multiworld.world.save";
	}

	public void execute() {
		boolean save = this.exists(0, "save");
		if (!save) {
			return;
		}
		String name;
		if (args.length == 1) {
			if (!this.isPlayer()) {
				this.send("You must specify a world!");
				return;
			} else {
				Player p = (Player) this.getSender();
				name = p.getWorld().getName();
			}
		} else {
			name = args[1];
		}
		if (name.equals("*")) {
			this.send("Saving all worlds...");
			for (MWorld world : MultiWorld.worlds) {
				MultiWorld.saveWorld(world.getWorld());
			}
			this.send("Saved all worlds.");
			return;
		}
		MWorld world = MultiWorld.getWorld(name);
		if (world == null) {
			this.send("A world with this name isn't loaded: " + name);
			return;
		}
		MultiWorld.saveWorld(world.getWorld());
		this.send("Saved \"" + MList.getColor(world) + name + ChatColor.WHITE + "\"");
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw save " + ChatColor.GRAY + "[world_name]");
		this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - World to save; " + ChatColor.GREEN + "*" + ChatColor.WHITE + " saves all worlds");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (Perm.has(this.getSender(), this.perm) ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Saves specified world");
	}
}
