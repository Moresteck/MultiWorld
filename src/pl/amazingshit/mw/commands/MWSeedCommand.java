package pl.amazingshit.mw.commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.amazingshit.mw.mw;
import pl.amazingshit.mw.util.PInfo;

public class MWSeedCommand {

	public static void handle(CommandSender cmdsender, String[] args, PInfo sender) {
		if (!sender.isAuthorized("multiworld.use.seed")) {
			return;
		}
		if (args.length == 2) {
			World world = mw.instance.getServer().getWorld(args[1]) == null ? null : mw.instance.getServer().getWorld(args[1]);
			if (world != null) {
				if (sender.isPlayer()) {
					cmdsender.sendMessage("§aSeed of §e" + world.getName() + "§a: §c" + world.getId());
				}
				if (!sender.isPlayer()) {
					cmdsender.sendMessage("Seed of " + world.getName() + ": " + world.getId());
				}
				return;
			}
			if (sender.isPlayer()) {
				cmdsender.sendMessage("§cWorld does not exists!");
			}
			if (!sender.isPlayer()) {
				cmdsender.sendMessage("World does not exists!");
			}
			return;
		}
		if (sender.isPlayer()) {
			Player p = ((Player)cmdsender);
			cmdsender.sendMessage("§aSeed of §e" + p.getWorld().getName() + "§a: §c" + p.getWorld().getId());
		}
		else {
			cmdsender.sendMessage("Please specify world!");
			return;
		}
	}
}