package pl.daamazingshit.mw.commands;

import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;

import pl.daamazingshit.mw.managers.ConfigWorld;
import pl.daamazingshit.mw.managers.WorldManager;
import pl.daamazingshit.mw.util.Sender;

public class MWListCommand {

	public static void handle(CommandSender sender, String alias, Sender s) {
		List<String> send = ConfigWorld.getWorldStringList();
		sender.sendMessage("World list:");
		for (String world: send) {
			if (s.isPlayer()) {
				String color = "§a";
				WorldManager wm = new WorldManager(world);
				if (wm.dimension() == Environment.NETHER) {
					color = "§c";
				}
				sender.sendMessage(" - " + color + world);
			}
			else {
				sender.sendMessage(" - " + world);
			}
		}
	}
}