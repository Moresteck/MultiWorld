package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;

public class MTeleport extends MCommand {

	public MTeleport(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.player.teleport");
	}

	public void execute() {
		if (!this.getCommand().isMW()) return;
		boolean tp = this.exists(0, "tp");
		if (!tp) return;
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
		int arg = args.length;
		if (arg == 1) {
			this.displayCommandHelp();
			return;
		} else if (arg >= 2) {
			String worldname = args[1];
			Player p = null;
			MWorld world = null;
			if (arg == 3) {
				p = MultiWorld.server.getPlayer(args[2].replace("\"", ""));
				if (args[2].startsWith("\"") && args[2].endsWith("\"")) {
					if (BukkitVersion.getVersionId() >= 14) {
						p = MultiWorld.server.getPlayerExact(args[2].replace("\"", ""));
					}
				}
				if (p == null) {
					this.send("Couldn't find a player with that name.");
					return;
				}
			} else {
				if (!(this.getSender() instanceof Player)) {
					this.send("You must specify the player!");
					return;
				}
				p = (Player) this.getSender();
			}
			world = MultiWorld.getWorld(worldname);
			if (world == null) {
				this.send("A world with this name isn't loaded: " + ChatColor.RED + worldname);
				return;
			}
			Location toTp = world.getWorld().getSpawnLocation();

			// b1.2_01-
			if (BukkitVersion.getVersionId() <= 1) {
				p.teleportTo(toTp);
			} else {
				p.teleport(toTp);
			}
			String who = this.getSender() == p ? "you" : p.getName();
			String tel = (this.getSender() instanceof Player) ? ((Player)this.getSender()).getName() : "Console";

			this.send("Teleported " + ChatColor.YELLOW + who + ChatColor.WHITE + " to " + ChatColor.GREEN + worldname);
			p.sendMessage("You've been teleported by " + ChatColor.YELLOW + tel + ChatColor.WHITE + " to " + ChatColor.GREEN + worldname);
		}
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw tp " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " [player]");
		this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - World to teleport to, e.g.: " + ChatColor.YELLOW + "survival");
		this.send(ChatColor.DARK_GRAY + " player" + ChatColor.WHITE + " - Who to teleport");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Teleports the player to specified world");
	}
}
