package pl.daamazingshit.mw.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import pl.daamazingshit.mw.mw;
import pl.daamazingshit.mw.managers.WorldManager;
import pl.daamazingshit.mw.util.Sender;

public class MWRemoveCommand {

	public static void handle(CommandSender cmdsender, String[] arguments, Sender sender) {
		if (!sender.isAuthorized("multiworld.manage.remove")) {
			return;
		}
		World world = mw.instance.getServer().getWorld(arguments[1]) != null ? mw.instance.getServer().getWorld(arguments[1]) : null;
		if (world == null) {
			if (sender.isPlayer()) {
				cmdsender.sendMessage("§cWorld with given name doesn't exists: §e" + arguments[1]);
			}
			if (!sender.isPlayer()) {
				cmdsender.sendMessage("World with given name doesn't exists: " + arguments[1]);
			}
			return;
		}
		WorldManager wm = new WorldManager(world.getName(), world.getEnvironment(), world.getId());
		boolean done = false;
		try {
			wm.remove();
			done = true;
		}
		catch (Exception ex) {
			done = false;
		}
		if (sender.isPlayer()) {
			cmdsender.sendMessage(done == true ? "§aComplete." : "§cSomething went wrong!");
		}
		if (!sender.isPlayer()) {
			cmdsender.sendMessage(done == true ? "Complete." : "Something went wrong!");
		}
		return;
	}
}