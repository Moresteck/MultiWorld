package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;
import pl.moresteck.multiworld.world.MWorld;

public class MWho extends MCommand {

	public MWho(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.perm = "multiworld.info.who";
	}

	public void execute() {
		boolean who = this.exists(0, "who");
		if (!who) {
			return;
		}
		MWorld world = null;
		if (args.length == 1) {
			// mw who
			if (!this.isPlayer()) {
				this.send("You must specify the world!");
				return;
			} else {
				Player p = (Player) this.getSender();
				world = MultiWorld.getWorld(p.getWorld().getName());
			}
		} else {
			// mw who [world]
			String name = args[1];
			world = MultiWorld.getWorld(name);
			if (world == null) {
				this.send("A world with this name isn't loaded: " + ChatColor.RED + name);
				return;
			}
		}
		this.send("Players in world \"" + MList.getColor(world) + world.getName() + ChatColor.WHITE + "\":");
		for (Player p : MultiWorld.server.getOnlinePlayers()) {
			if (p.getWorld().getName().equals(world.getName())) {
				this.send(" - " + ChatColor.YELLOW + p.getName() + ChatColor.WHITE + (p.getDisplayName().equals(p.getName()) ? ", nick: " + ChatColor.YELLOW + p.getDisplayName() : ""));
			}
		}
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw who " + ChatColor.GRAY + "[world_name]");
		this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - From which world to list players");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (Perm.has(this.getSender(), this.perm) ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Shows who is currently playing in the specified world");
	}
}
