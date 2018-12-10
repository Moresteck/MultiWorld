package pl.moresteck.multiworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
/**
 * Supports Permissions 2.5+ (might work on a lower version if you have one)
 */
public class Perm {
	public static com.nijikokun.bukkit.Permissions.Permissions permissions = null;

	public static void setPermissions(Plugin p) {
		try {
			if (p instanceof com.nijikokun.bukkit.Permissions.Permissions) {
				permissions = (com.nijikokun.bukkit.Permissions.Permissions) p;
				MultiWorld.log.info("[MultiWorld] Found Permissions v" + permissions.getDescription().getVersion() + ". Using it for permissions.");
			}
		} catch (Exception ex) {
			MultiWorld.log.info("[MultiWorld] Could not find Permissions. Using OP system.");
			permissions = null;
		}
	}

	/** Permissions:
	 * <pre>
	 *  multiworld.*:
	 *   multiworld.world.*:
	 *    multiworld.world.save
	 *    multiworld.world.create
	 *    multiworld.world.import
	 *    multiworld.world.setspawn
	 *    multiworld.world.unload
	 *    multiworld.world.info
	 *   multiworld.info.*:
	 *    multiworld.info.list
	 *    multiworld.info.who
	 *   multiworld.player.*:
	 *    multiworld.player.teleport
	 * </pre>
	 */
	public static boolean has(CommandSender cs, String perm) {
		if (!(cs instanceof Player)) {
			return true;
		}
		Player p = (Player)cs;
		if (permissions == null) {
			return p.isOp();
		}

		if (perm.equals("")) {
			return true;
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
