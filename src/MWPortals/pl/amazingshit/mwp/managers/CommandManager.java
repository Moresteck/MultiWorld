package MWPortals.pl.amazingshit.mwp.managers;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import MWPortals.pl.amazingshit.mwp.mwp;
import MWPortals.pl.amazingshit.mwp.listeners.PortalListener;

public class CommandManager {

	public static void handleCommand(CommandSender sender, String[] args, String alias) {
		if (alias.equalsIgnoreCase("portal")) {
			if (args.length == 0) {
				sender.sendMessage("Not enough arguments.");
				return;
			}
			if (args[0].equalsIgnoreCase("create")) {
				if (!(sender instanceof Player)) {
					return;
				}
				Player p = (Player)sender;
				if (args.length == 1) {
					sender.sendMessage("Not enough arguments.");
					return;
				}
				if (PortalListener.pos1.get(p) == null || PortalListener.pos2.get(p) == null) {
					p.sendMessage("Please select an area!");
					return;
				}
				if (PortalManager.exists(args[1])) {
					p.sendMessage("A portal already exists with that name.");
					return;
				}
				if (args.length == 3) {
					Location pos1 = PortalListener.pos1.get(p);
					Location pos2 = PortalListener.pos1.get(p);
					if (args[2].startsWith("world:")) {
						String[] toworld = args[2].split(":");
						if (mwp.instance.getServer().getWorld(toworld[1]) != null) {
							PortalManager.createPortal(args[1], pos1, pos2, mwp.instance.getServer().getWorld(toworld[1]).getName());
							p.sendMessage("Created portal '" + args[1] + "' with world destination '" + toworld[1] + "'");
							return;
						}
						p.sendMessage("Destination doesn't exists.");
						return;
					}
					if (args[2].startsWith("portal:")) {
						String[] portal = args[2].split(":");
						if (PortalManager.exists(portal[1])) {
							PortalManager.createLPortal(args[1], pos1, pos2, portal[1]);
							p.sendMessage("Created portal '" + args[1] + "' with portal destination '" + portal[1] + "'");
							return;
						}
						p.sendMessage("Destination doesn't exists.");
						return;
					}
				}
				Location pos1 = PortalListener.pos1.get(p);
				Location pos2 = PortalListener.pos1.get(p);
				PortalManager.createPortal(args[1], pos1, pos2, null);
				p.sendMessage("Created portal '" + args[1] + "' with no destination.");
				return;
			}
		}
		if (args[0].equalsIgnoreCase("remove")) {
			if (args.length == 1) {
				sender.sendMessage("Not enough arguments.");
				return;
			}
			if (PortalManager.allPortals().contains(args[1])) {
				PortalManager.removePortal(args[1]);
				sender.sendMessage("Removed portal '" + args[1] + "'");
				return;
			}
			sender.sendMessage("Non-existent portal specified.");
			return;
		}
		if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage("Portal list:");
			if (PortalManager.allPortals().isEmpty()) {
				return;
			}
			for (String portal: PortalManager.allPortals()) {
				sender.sendMessage(" - " + portal);
			}
			return;
		}
	}
}