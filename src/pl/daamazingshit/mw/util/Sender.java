package pl.daamazingshit.mw.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sender {

	private CommandSender cs;

	public Sender(CommandSender c) {
		this.cs = c;
	}

	public Boolean isPlayer() {
		return (cs instanceof Player) ? true : false;
	}
}
