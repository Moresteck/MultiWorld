package pl.amazingshit.mw.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.amazingshit.mw.managers.ConfigWorld;
import pl.amazingshit.mw.managers.Help;
import pl.amazingshit.mw.util.PInfo;
import pl.amazingshit.mw.util.PropertyType;

public class WSAnimalsCommand {

	public static void handle(CommandSender cmdsender, String[] arguments, PInfo sender) {
		if (sender.isPlayer() && !(sender.isAuthorized("multiworld.settings.animals"))) {
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
		boolean animals = Boolean.parseBoolean(arguments[1].toLowerCase());
		if (arguments.length == 3) {
			boolean done = ConfigWorld.setAllow(PropertyType.ANIMALS, arguments[2], animals);
			if (sender.isPlayer()) {
			    cmdsender.sendMessage(done == true ? "§aSuccess! §fSet the property '§banimals§f' to '§c" + animals + "§f' in world '§a"+arguments[2]+"§f'" : "§cSomething went wrong!"); }
			if (!sender.isPlayer()) {
			    cmdsender.sendMessage(done == true ? "Success! Set the property 'animals' to '" + animals + "' in world '"+arguments[2]+"'" : "Something went wrong!"); }
			return;
		}
		if (arguments.length == 2) {
			if (!sender.isPlayer()) {
				cmdsender.sendMessage("Not enough arguments");
				return; }
			String world = ((Player)cmdsender).getWorld().getName();
			boolean done = ConfigWorld.setAllow(PropertyType.ANIMALS, world, animals);
			if (sender.isPlayer()) {
			    cmdsender.sendMessage(done == true ? "§aSuccess! §fSet the property '§banimals§f' to '§c" + animals + "§f' in world '§a"+world+"§f'" : "§cSomething went wrong!"); }
			if (!sender.isPlayer()) {
				cmdsender.sendMessage(done == true ? "Success! Set the property 'animals' to '" + animals + "' in world '"+world+"'" : "Something went wrong!"); }
			return;
		}
	}
}