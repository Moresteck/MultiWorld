package pl.moresteck.multiworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;
/**
 * Supports Permissions 2.5+ (might work on a lower version if you have one)
 */
public class Perm {
	public static Permissions permissions = null;

	public void setPermissions(Plugin p) {
		try {
			if (p instanceof Permissions) {
				permissions = (Permissions) p;
				MultiWorld.log.info(" [MultiWorld] Found Permissions v" + permissions.getDescription().getVersion() + ". Using it for permissions.");
			}
		} catch (Exception ex) {
			MultiWorld.log.info(" [MultiWorld] Could not find Permissions. Using OP system.");
			permissions = null;
		}
	}

	public static boolean has(CommandSender cs, String perm) {
		if (!(cs instanceof Player)) {
			return true;
		}
		Player p = (Player)cs;
		if (permissions == null) {
			return p.isOp();
		}

		boolean hasPrecise = permissions.getHandler().has(p, perm);

		// Every permission.
		boolean hasAll = permissions.getHandler().has(p, "multiworld.*");

		// Such as world creation, importing etc.
		boolean hasAllWorld = permissions.getHandler().has(p, "multiworld.world.*");

		// Such as player teleporting.
		boolean hasAllPlayer = permissions.getHandler().has(p, "multiworld.player.*");

		// Such as world list, player list in world.
		boolean hasAllInfo = permissions.getHandler().has(p, "multiworld.info.*");

		if (!hasPrecise) {
			if (hasAll) {
				return true;
			} else if (hasAllWorld && perm.startsWith("multiworld.world")) {
				return true;
			} else if (hasAllPlayer && perm.startsWith("multiworld.player")) {
				return true;
			} else if (hasAllInfo && perm.startsWith("multiworld.info")) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
