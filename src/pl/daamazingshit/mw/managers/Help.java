package pl.daamazingshit.mw.managers;

import org.bukkit.command.CommandSender;
import pl.daamazingshit.mw.util.Sender;

public class Help {
// §
	public static void showHelp(CommandSender sender) {
		Sender s = new Sender(sender);
		if (sender == null)
			return;
		if (s.isPlayer()) {
			sender.sendMessage("§e  MultiWorld help");
			sender.sendMessage("§b /mw create <world> [environment | seed] [seed]");
			sender.sendMessage("§b /mw remove <world>");
			sender.sendMessage("§b /worldsettings");
		}
		else {
			sender.sendMessage("  MultiWorld help");
			sender.sendMessage(" /mw create <world> [environment | seed] [seed]");
			sender.sendMessage(" /mw remove <world>");
			sender.sendMessage(" /worldsettings");
		}
	}

	public static void showSettingsHelp(CommandSender sender) {
		
	}
}
