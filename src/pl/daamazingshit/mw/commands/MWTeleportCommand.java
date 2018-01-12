package pl.daamazingshit.mw.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.daamazingshit.mw.mw;
import pl.daamazingshit.mw.managers.ConfigWorld;
import pl.daamazingshit.mw.managers.WorldManager;
import pl.daamazingshit.mw.util.Sender;

public class MWTeleportCommand {

	public static void handle(CommandSender cmdsender, String[] arguments, Sender sender) {
		if (!sender.isAuthorized("multiworld.use.teleport")) {
			return;
		}
		if (arguments.length == 1) {
			if (sender.isPlayer()) {
				cmdsender.sendMessage("§cYou need to specify world!");
				return ;
			}
			cmdsender.sendMessage("You need to specify world!");
		}
		if (arguments.length == 2) {
			if (!sender.isPlayer()) {
				cmdsender.sendMessage("You cannot teleport console.");
				return;
			}
			Player toTp = (Player)cmdsender;
			if (!ConfigWorld.exists(arguments[1])) {
				cmdsender.sendMessage("§cWorld could not be recognised.");
				return;
			}
			WorldManager wm = new WorldManager(arguments[1]);
			toTp.teleport(wm.world().getSpawnLocation());
			toTp.sendMessage("§aTeleported to " + arguments[1]);
		}
		if (arguments.length == 3) {
			Player toTp = mw.instance.getServer().getPlayer(arguments[2]);
			if (toTp == null) {
				if (sender.isPlayer()) {
					cmdsender.sendMessage("§cThere is no player with name " + arguments[2] + " online.");
				}
				if (!sender.isPlayer()) {
					cmdsender.sendMessage("There is no player with name " + arguments[2] + " online.");
				}
				return;
			}
			if (!ConfigWorld.exists(arguments[1])) {
				if (sender.isPlayer()) {
					cmdsender.sendMessage("§cWorld could not be recognised.");
					return;
				}
				cmdsender.sendMessage("World could not be recognised.");
				return;
			}
			WorldManager wm = new WorldManager(arguments[1]);
			toTp.teleport(wm.world().getSpawnLocation());
			toTp.sendMessage("§aTeleported to " + arguments[1] + " by " + sender.getName());
			if (sender.isPlayer()) {
				cmdsender.sendMessage("§aTeleported " + arguments[2] + " to " + arguments[1]);
				return;
			}
			cmdsender.sendMessage("Teleported " + arguments[2] + " to " + arguments[1]);
		}
		return;
	}
}