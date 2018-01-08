package pl.daamazingshit.mw.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nijikokun.bukkit.Permissions.Permissions;
/**
 * Gets information about sender.
 */
public class Sender {

	private CommandSender cs;

	public Sender(CommandSender c) {
		this.cs = c;
	}

	public Boolean isPlayer() {
		return (cs instanceof Player) ? true : false;
	}

	public Boolean isAuthorized(String permission) {
		if (this.isPlayer()) {
			Player p = (Player)cs;
			if (p.isOp() || Permissions.Security.has(p, permission)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}
	}
}
