package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

import pl.moresteck.bvnpe.BukkitVersion;
import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;

public class MSetSpawn extends MCommand {

	public MSetSpawn(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.perm = "multiworld.world.setspawn";
	}

	public void execute() {
		boolean setspawn = this.exists(0, "setspawn");
		if (!setspawn) {
			return;
		}
		if (!Perm.has(this.getSender(), this.perm)) {
			this.send("No permission!");
			return;
		}
		Player p = null;
		Location now = null;
		if (this.getSender() instanceof Player) {
			p = (Player) this.getSender();
			now = p.getLocation();
		} else {
			this.send("You can't set spawn from console.");
			return;
		}
		// b1.3+
		if (BukkitVersion.getVersionId() >= 2) {
			p.getWorld().setSpawnLocation(now.getBlockX(), now.getBlockY(), now.getBlockZ());
		} else {
			net.minecraft.server.World world = ((CraftWorld)p.getWorld()).getHandle();
			world.spawnX = now.getBlockX();
			world.spawnY = now.getBlockY();
			world.spawnZ = now.getBlockZ();
			MultiWorld.saveWorld(p.getWorld());
		}
		this.send("New spawn location set!");
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw setspawn");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (Perm.has(this.getSender(), this.perm) ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Sets the spawn point for this world");
	}
}
