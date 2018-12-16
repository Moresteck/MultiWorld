package pl.moresteck.multiworld;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijikokun.bukkit.Permissions.Permissions;
/**
 * Compatible with:
 *  PermissionsEx v1.00+
 *  Permissions v1.0+
 */
public class Perm {
	public static Permissions permissions = null;
	public static PermissionManager permissionsex = null;

	public static void setupPermissions() {
		try {
			Plugin p = null;
			if ((p = MultiWorld.server.getPluginManager().getPlugin("Permissions")) != null) {
				permissions = (Permissions) p;
				MultiWorld.log.info("[MultiWorld] Found Permissions v" + p.getDescription().getVersion() + ". Using it for permissions.");
			}
			if ((p = MultiWorld.server.getPluginManager().getPlugin("PermissionsEx")) != null && permissions == null) {
				permissionsex = PermissionsEx.getPermissionManager();
				MultiWorld.log.info("[MultiWorld] Found PermissionsEx v" + p.getDescription().getVersion() + ". Using it for permissions.");
			}
		} catch (Exception ex) {
			MultiWorld.log.info("[MultiWorld] Could not find any permission plugin. Using OP system.");
		}
	}

	public static boolean isPermissionsLoaded() {
		return permissions != null || permissionsex != null;
	}

	/** Permission nodes:
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
	 *   multiworld.portal.*:
	 *    multiworld.portal.list
	 *    multiworld.portal.create
	 *    multiworld.portal.remove
	 *    multiworld.portal.select
	 *    multiworld.portal.modify
	 *    multiworld.portal.destination.read
	 *    multiworld.portal.destination.set
	 * </pre>
	 */
	public static boolean has(CommandSender cs, String perm) {
		if (!(cs instanceof Player)) {
			return true;
		}
		Player p = (Player)cs;
		if (!isPermissionsLoaded()) {
			return p.isOp();
		}

		if (perm.equals("")) {
			return true;
		}

		boolean hasPrecise = permissions != null ? permissions.getHandler().has(p, perm) : permissionsex.has(p, perm);

		// Every permission.
		boolean hasAll = permissions != null ? permissions.getHandler().has(p, "multiworld.*") : permissionsex.has(p, "multiworld.*");

		// Such as world creation, importing etc.
		boolean hasAllWorld = permissions != null ? permissions.getHandler().has(p, "multiworld.world.*") : permissionsex.has(p, "multiworld.world.*");

		// Such as player teleporting.
		boolean hasAllPlayer = permissions != null ? permissions.getHandler().has(p, "multiworld.player.*") : permissionsex.has(p, "multiworld.player.*");

		// Such as world list, player list in world.
		boolean hasAllInfo = permissions != null ? permissions.getHandler().has(p, "multiworld.info.*") : permissionsex.has(p, "multiworld.info.*");

		// Such as world list, player list in world.
		boolean hasAllPortal = permissions != null ? permissions.getHandler().has(p, "multiworld.portal.*") : permissionsex.has(p, "multiworld.portal.*");

		if (!hasPrecise) {
			if (hasAll) {
				return true;
			} else if (hasAllWorld && perm.startsWith("multiworld.world")) {
				return true;
			} else if (hasAllPlayer && perm.startsWith("multiworld.player")) {
				return true;
			} else if (hasAllInfo && perm.startsWith("multiworld.info")) {
				return true;
			} else if (hasAllPortal && perm.startsWith("multiworld.portal")) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
