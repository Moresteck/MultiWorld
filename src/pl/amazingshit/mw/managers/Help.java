package pl.amazingshit.mw.managers;

import org.bukkit.command.CommandSender;

import pl.amazingshit.mw.mw;
import pl.amazingshit.mw.util.PInfo;

public class Help {
// �
	public static void showHelp(CommandSender sender) {
		PInfo s = new PInfo(sender);
		if (sender == null)
			return;
		if (s.isPlayer()) {
			sender.sendMessage("�e  MultiWorld help");
			sender.sendMessage("�b /mw create <world> [environment] [seed]");
			sender.sendMessage("�b /mw remove <world>");
			sender.sendMessage("�b /mw tp <world> [player]");
			sender.sendMessage("�b /mw seed [world]");
			if (mw.someutilsEnabled) {
				sender.sendMessage("�b /mw who [world]");
			}
			sender.sendMessage("�b /worldlist");
			sender.sendMessage("�b /worldsettings");
		}
		else {
			sender.sendMessage("  MultiWorld help");
			sender.sendMessage(" /mw create <world> [environment] [seed]");
			sender.sendMessage(" /mw remove <world>");
			sender.sendMessage(" /mw tp <world> <player>");
			sender.sendMessage(" /mw seed <world>");
			if (mw.someutilsEnabled) {
				sender.sendMessage(" /mw who <world>");
			}
			sender.sendMessage(" /worldlist");
			sender.sendMessage(" /worldsettings");
		}
	}

	public static void showSettingsHelp(CommandSender sender) {
		PInfo s = new PInfo(sender);
		if (sender == null)
			return;
		if (s.isPlayer()) {
			sender.sendMessage("�e  WorldSettings help (MultiWorld)");
			sender.sendMessage("�b /ws pvp <true | false> [world]");
			sender.sendMessage("�b /ws animals <true | false> [world]");
			sender.sendMessage("�b /ws monsters <true | false> [world]");
		}
		else {
			sender.sendMessage("  WorldSettings help (MultiWorld)");
			sender.sendMessage(" /ws pvp <true | false> <world>");
			sender.sendMessage(" /ws animals <true | false> <world>");
			sender.sendMessage(" /ws monsters <true | false> <world>");
		}
	}
}