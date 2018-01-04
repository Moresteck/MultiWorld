package pl.daamazingshit.mw.managers;

import org.bukkit.command.CommandSender;
import pl.daamazingshit.mw.util.Sender;

public class Help {
// §
	public static void showHelp(CommandSender cs) {
		Sender s = new Sender(cs);
		if (s.isPlayer()) {
			cs.sendMessage("§e  MultiWorld help");
			cs.sendMessage("§b /mw create <world> [environment | seed] [seed]");
			cs.sendMessage("§b /mw remove <world>");
			cs.sendMessage("§b /worldsettings");
		}
		else {
			cs.sendMessage("  MultiWorld help");
			cs.sendMessage(" /mw create <world> [environment | seed] [seed]");
			cs.sendMessage(" /mw remove <world>");
			cs.sendMessage(" /worldsettings");
		}
	}
}
