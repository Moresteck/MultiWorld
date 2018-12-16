package pl.moresteck.multiworld.portal.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.commands.MCommand;
import pl.moresteck.multiworld.portal.Portal;
import pl.moresteck.multiworld.portal.Portal.Destination;

public class PDestination extends MCommand {

	public PDestination(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.portal.destination.*");
	}

	public void execute() {
		if (!this.getCommand().isMWP()) return;
		if (!this.exists(0, "destination")) return;

		int arg = this.getArgs().length;
		if (arg == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
			this.setPermission("multiworld.portal.destination.read");
			if (!this.hasPermission()) {
				this.send("No permission!");
				return;
			}
			this.displayPortalInfo(new Portal(args[1]));
			return;
		}
		if (arg == 1) {
			this.displayCommandHelp();
			return;
		}
		this.setPermission("multiworld.portal.destination.set");
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}
		Location newDest = null;
		if (args[2].startsWith("W:")) {
			String[] split = args[2].split(":", 2);
			if (split.length != 2) {
				this.send("World not defined. W:worldname");
				return;
			}
			newDest = MultiWorld.server.getWorld(split[1]).getSpawnLocation();
		} else if (args[2].equalsIgnoreCase("me")) {
			if (this.isPlayer()) {
				newDest = ((Player) this.getSender()).getLocation();
			} else {
				this.send("You are the console!");
				return;
			}
		} else {
			this.send("Undefined destination type.");
			return;
		}
		if (!Portal.portalExists(args[1])) {
			this.send("A portal with this name isn't defined!");
			return;
		}
		Portal portal = new Portal(args[1]);
		boolean set = portal.setDestination(newDest);
		if (set) this.send("New destination for '" + portal.getName() + "' set: " + ChatColor.GRAY + MultiWorld.locToString(newDest));
		else this.send("Something went wrong while setting a new destination!");
	}

	public void displayPortalInfo(Portal portal) {
		if (!Portal.portalExists(args[1])) {
			this.send("A portal with this name isn't defined!");
			return;
		}
		Location[] loc = portal.getLocations();
		Location dest = portal.getDestination();
		this.send(ChatColor.GREEN + "====== Portal Info ======");
		this.send(ChatColor.BLUE + "Location 1: " + ChatColor.GRAY + MultiWorld.locToString(loc[0]));
		this.send(ChatColor.BLUE + "Location 2: " + ChatColor.GRAY + MultiWorld.locToString(loc[1]));
		this.send(ChatColor.BLUE + "Destination: " + ChatColor.GRAY + (portal.getDestinationType() == Destination.WORLD ? dest.getWorld().getName() + "'s spawn" : MultiWorld.locToString(dest)));
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mwp destination " + ChatColor.GRAY + "<portal> " + ChatColor.BLUE + "[destination]");
		this.send(ChatColor.DARK_GRAY + " portal" + ChatColor.WHITE + " - Portal to which you desire changing destination");
		this.send(ChatColor.DARK_GRAY + " destination" + ChatColor.WHITE + " - Destination world to set, e.g. " + ChatColor.YELLOW + "W:skyblock" + 
				ChatColor.WHITE + ". " + ChatColor.YELLOW + "me" + ChatColor.WHITE + " sets destination to your current location");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Gets or sets the destination of specified portal");
	}
}
