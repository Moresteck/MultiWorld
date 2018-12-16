package pl.moresteck.multiworld.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;

public class MWho extends MCommand {

	public MWho(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.info.who");
	}

	public void execute() {
		if (!this.getCommand().isMW()) return;
		boolean who = this.exists(0, "who");
		if (!who) return;
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
				this.send("A world with this name isn't loaded: " + ChatColor.GRAY + name);
				return;
			}
		}
		this.send("Players in world \"" + MList.getColor(world) + world.getName() + ChatColor.WHITE + "\" (" + Online.getPlayers(world.getWorld()).size() + "):");
		for (String p : Online.getPlayers(world.getWorld())) {
			this.send(" - " + ChatColor.YELLOW + p);
		}
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw who " + ChatColor.GRAY + "[world_name]");
		this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - From which world to list players");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Shows who is currently playing in the specified world");
	}

	static class Online {
		public static List<String> getPlayers(World world) {
			List<String> players = new LinkedList<String>();
			for (Player p : MultiWorld.server.getOnlinePlayers()) {
				if (p.getWorld().getName().equals(world.getName())) {
					players.add(p.getName());
				}
			}
			return players;
		}
	}
}
