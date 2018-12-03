package pl.moresteck.multiworld.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.bvnpe.BukkitVersion;
import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;
import pl.moresteck.multiworld.world.MWorld;

public class MInfo extends MCommand {

	public MInfo(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.perm = "multiworld.world.info";
	}

	public void execute() {
		boolean info = this.exists(0, "info");
		if (!info) {
			return;
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
		}
		if (!Perm.has(this.getSender(), this.perm)) {
			this.send("No permission!");
			return;
		}
		int arg = args.length;
		if (arg == 1) {
			if (this.isPlayer()) {
				Player p = (Player) this.getSender();
				MWorld world = MultiWorld.getWorld(p.getWorld().getName());
				this.displayInfo(world, "1");
			} else {
				this.send("You must specify the name!");
			}
			return;
		} else {
			String name = args[1];
			MWorld world = MultiWorld.getWorld(name);
			if (world == null) {
				this.send("This world isn't loaded: " + ChatColor.RED + name);
				return;
			} else {
				if (arg == 3) {
					this.displayInfo(world, args[2]);
				} else {
					this.displayInfo(world, "1");
				}
			}
		}
	}

	public void displayInfo(MWorld world, String page) {
		this.send(ChatColor.GREEN + "======= World Info - Page " + page + " =======");
		if (page.startsWith("1")) {
			this.send(ChatColor.GRAY + "Name: " + ChatColor.BLUE + world.getName());
			this.send(ChatColor.GRAY + "Environment: " + MList.getColor(world) + world.getEnvironment().name().toLowerCase());
			this.send(ChatColor.GRAY + "Seed: " + ChatColor.GOLD + world.getSeed());
			this.send(ChatColor.GRAY + "Gen arguments: " + (BukkitVersion.getVersionId() >= 9 ? ChatColor.GOLD + world.getGenerator() : ChatColor.RED + "unavailable"));
			this.send(" For more info type: " + ChatColor.YELLOW + "/mw info " + world.getName() + " 2");
		} else if (page.startsWith("2")) {
			this.send(ChatColor.GRAY + "PVP: " + getColor(world.getPvP()));
			this.send(ChatColor.GRAY + "Monster spawning: " + getColor(world.getAllowMonsters()));
			this.send(ChatColor.GRAY + "Animal spawning: " + getColor(world.getAllowAnimals()));
			if (BukkitVersion.getVersionId() >= 4) {
				String is = (world.getWeather() ? ChatColor.GREEN + "on, " : ChatColor.RED + "off, ");
				String now = (world.getWorld().isThundering() ? ChatColor.BLUE + "now stormy" : ChatColor.YELLOW + "now sunny");
				this.send(ChatColor.GRAY + "Weather: " + is + now);
			} else {
				this.send(ChatColor.GRAY + "Weather: " + ChatColor.RED + "unavailable");
			}
		}
	}

	public String getColor(boolean bol) {
		if (bol) return ChatColor.GREEN + "allowed";
		else return ChatColor.RED + "disallowed";
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mw info " + ChatColor.GRAY + "[world_name] [page]");
		this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - Which world's info to view");
		this.send(ChatColor.DARK_GRAY + " page" + ChatColor.WHITE + " - Info page");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (Perm.has(this.getSender(), this.perm) ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Shows some information about specified world");
	}
}
