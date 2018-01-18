package pl.amazingshit.mw.commands;

import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;

import pl.amazingshit.mw.mw;
import pl.amazingshit.mw.managers.Help;
import pl.amazingshit.mw.managers.WorldManager;
import pl.amazingshit.mw.util.PInfo;

public class MWCreateCommand {

	public static void handle(CommandSender cmdsender, String[] arguments, PInfo sender) {
		if (!sender.isAuthorized("multiworld.manage.create")) {
			return;
		}
		if (arguments.length == 1) {
			Help.showHelp(cmdsender);
			return;
		}
		WorldManager wm = null;
		// If create command contains world's name
		if (arguments.length == 2) {
			wm = new WorldManager(arguments[1]);
		}
		// If create command contains world's name and environment
		if (arguments.length == 3) {
			try {
				// Done if argument 3 is environment
				Environment env = Environment.valueOf(arguments[2].toUpperCase());
				wm = new WorldManager(arguments[1], env);
			}
			catch (Exception ex) {
				if (sender.isPlayer()) {
					cmdsender.sendMessage("§cEnvironment is invalid.");
				}
				else {
					cmdsender.sendMessage("Environment is invalid.");
				}
				return;
			}
		}
		// If create command contains world's name, environment and seed
		if (arguments.length == 4) {
			try {
				wm = new WorldManager(arguments[1], Environment.valueOf(arguments[2].toUpperCase()), Long.parseLong(arguments[3]));
			}
			catch (Exception ex) {
				if (sender.isPlayer()) {
				    cmdsender.sendMessage("§cInvalid format.");
				} if (!sender.isPlayer()) {
				    cmdsender.sendMessage("Invalid format."); }
				Help.showHelp(cmdsender);
				return;
			}
		}
		if (wm == null) {
			if (sender.isPlayer()) {
			    cmdsender.sendMessage("§cSomething went wrong! Please try again.");
			} if (!sender.isPlayer()) {
			    cmdsender.sendMessage("Something went wrong! Please try again."); }
			return;
		}
		if (sender.isPlayer()) {
			mw.instance.getServer().broadcastMessage("§aTrying to create a new world: " + arguments[1]);
		} if (!sender.isPlayer()) {
			mw.instance.getServer().broadcastMessage("§aTrying to create a new world: " + arguments[1]);
			cmdsender.sendMessage("Trying to create a new world: " + arguments[1]); }
		boolean done = wm.create();
		if (sender.isPlayer()) {
			cmdsender.sendMessage(done == true ? "§aComplete." : "§cSomething went wrong!");
		} if (!sender.isPlayer()) {
			cmdsender.sendMessage(done == true ? "Complete." : "Something went wrong!"); }
		return;
	}
}