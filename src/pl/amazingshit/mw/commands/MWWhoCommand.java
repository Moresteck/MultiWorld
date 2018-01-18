package pl.amazingshit.mw.commands;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.amazingshit.mw.mw;
import pl.amazingshit.mw.util.PInfo;
import pl.amazingshit.su.SomeUtils;
import pl.amazingshit.su.bukkit.Bukkit;

public class MWWhoCommand {

	public static void handle(CommandSender cmdsender, String[] args, PInfo sender) {
		if (!mw.someutilsEnabled) {
			cmdsender.sendMessage("Couldn't found SomeUtils. Command won't proceed.");
			return;
		}
		if (!sender.isAuthorized("multiworld.use.who")) {
			return;
		}
		if (args.length == 1) {
			if (!sender.isPlayer()) {
				cmdsender.sendMessage("Please specify a world!");
				return;
			}
			Player p = (Player)cmdsender;
			List<String> players = SomeUtils.getPlayersManager().getWorldPlayerList(p.getWorld().getName());
			p.sendMessage("Players in '§a" + p.getWorld().getName() + "§f':");
			for (String player: players) {
				p.sendMessage(" - §b" + player);
			}
			return;
		}
		World world;
		if (Bukkit.getWorlds().contains(args[1])) {
			world = Bukkit.getServer().getWorld(args[1]);
			List<String> players = SomeUtils.getPlayersManager().getWorldPlayerList(world.getName());
			if (sender.isPlayer()) {
				cmdsender.sendMessage("Players in '§a" + world.getName() + "§f':");
				for (String player: players) {
					cmdsender.sendMessage(" - §b" + player);
				}
			}
			return;
		}
		else if (sender.isPlayer()) {
			cmdsender.sendMessage("§cThat world does not exists!");
		}
		else if (!sender.isPlayer()) {
			cmdsender.sendMessage("That world does not exists!");
		}
	}
}