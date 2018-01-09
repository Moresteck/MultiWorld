package pl.daamazingshit.mw.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.daamazingshit.mw.managers.ConfigWorld;
import pl.daamazingshit.mw.managers.Help;
import pl.daamazingshit.mw.util.PropertyType;
import pl.daamazingshit.mw.util.Sender;

public class WSPvPCommand {

	public static void handle(CommandSender cmdsender, String[] arguments, Sender sender) {
		if (sender.isPlayer() && !(sender.isAuthorized("multiworld.settings.pvp"))) {
			cmdsender.sendMessage("§cYou don't have permission.");
			return;
		}
		if (arguments.length == 1) {
			if (sender.isPlayer()) {
			    cmdsender.sendMessage("§cNot enough arguments.");
			} if (!sender.isPlayer()) {
			    cmdsender.sendMessage("Not enough arguments"); }
			Help.showSettingsHelp(cmdsender);
			return;
		}
		boolean pvp = Boolean.parseBoolean(arguments[1].toLowerCase());
		if (arguments.length == 3) {
			boolean done = ConfigWorld.setAllow(PropertyType.PVP, arguments[2], pvp);
			if (sender.isPlayer()) {
			    cmdsender.sendMessage(done == true ? "§aSuccess! §fSet the property '§bpvp§f' to '§c" + pvp + "§f' in world '§a"+arguments[2]+"§f'" : "§cSomething went wrong!"); }
			if (!sender.isPlayer()) {
			    cmdsender.sendMessage(done == true ? "Success! Set the property 'pvp' to '" + pvp + "' in world '"+arguments[2]+"'" : "Something went wrong!"); }
			return;
		}
		if (arguments.length == 2) {
			if (!sender.isPlayer()) {
				cmdsender.sendMessage("Not enough arguments");
				return; }
			String world = ((Player)sender).getWorld().getName();
			boolean done = ConfigWorld.setAllow(PropertyType.PVP, world, pvp);
			cmdsender.sendMessage(done == true ? "§aSuccess! §fSet the property '§bpvp§f' to '§c" + pvp + "§f' in world '§a"+world+"§f'" : "§cSomething went wrong!");
			return;
		}
	}
}