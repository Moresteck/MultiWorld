package pl.moresteck.multiworld.portal.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.commands.MCommand;
import pl.moresteck.multiworld.listeners.playerlistener;
import pl.moresteck.multiworld.portal.Portal;

public class PCreate extends MCommand {

	public PCreate(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.portal.create");
	}

	public void execute() {
		if (!this.getCommand().isMWP()) return;
		if (!this.exists(0, "create")) return;

		int arg = this.getArgs().length;
		if (arg == 1) {
			this.displayCommandHelp();
			return;
		}
		if (args[1].equalsIgnoreCase("help")) {
			this.displayCommandHelp();
			return;
		}
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}
		if (!this.isPlayer()) {
			this.send("You are the console!");
			return;
		}
		Player p = (Player) this.getSender();
		String name = args[1];
		if (Portal.portalExists(name)) {
			this.send("A portal with this name already exists!");
			return;
		}
		if (!playerlistener.points1.containsKey(p.getName()) || !playerlistener.points2.containsKey(p.getName())) {
			this.send("You didn't select your portal dimensions!");
			return;
		}
		boolean cooldown = false;
		if (arg == 3) if (args[2].equalsIgnoreCase("true")) cooldown = true;
		Location p1 = playerlistener.points1.get(p.getName());
		Location p2 = playerlistener.points2.get(p.getName());
		String dest = "W:" + MultiWorld.server.getWorlds().get(0).getName();
		Portal portal = Portal.createPortal(name, p1, p2, dest, cooldown);
		if (portal == null) {
			this.send("Something went wrong... But it shouldn't!");
			return;
		}
		this.send("Created a new portal '" + name + "' with destination set to '" + dest + "'");
		this.send("You can change it by typing '/mwp destination " + name + " [new_destination]'");
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		this.send(ChatColor.BLUE + "/mwp create " + ChatColor.GRAY + "<name> " + ChatColor.BLUE + "[cooldown]");
		this.send(ChatColor.DARK_GRAY + " name" + ChatColor.WHITE + " - New portal name");
		this.send(ChatColor.DARK_GRAY + " cooldown" + ChatColor.WHITE + " - Choose if you want that fancy portal cooldown before teleportation");
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Creates a new portal");
	}
}
