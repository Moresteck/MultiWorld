package pl.moresteck.bvnpe;

import java.util.logging.Logger;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitVersion {
	static Logger log = Logger.getLogger("Minecraft");
	public static String plugin_version = "0.4 NPE (Not Plugin Edition)";
	private static String version;

	// We aren't using String here because some third-party plugin could
	// fake the version number. This method isn't 100% secure tho...
	public static void setupVersion(final CraftServer server) {
		BukkitVersion.version = VersionParser.parseVersion(server.getVersion());
	}

	public static String getVersion() {
		return BukkitVersion.version;
	}

	public static int getVersionId() {
		// We aren't using protocol numbers because of inconsistent version numbers.
		if (version.equals("1.1")) {
			// It may be 1.1, 1.1_02 or 1.2.
			return 0;
		} else if (version.equals("1.2_01")) {
			// 1.2_02 vanilla server never existed.
			return 1;
		} else if (version.equals("1.3")) {
			return 2;
		} else if (version.equals("1.4")) {
			// No Bukkit for 1.4_01.
			return 3;
		} else if (version.equals("1.5_02")) {
			// No Bukkit for 1.5 and 1.5_01.
			return 4;
		} else if (version.equals("1.6.2")) {
			// No Bukkit for 1.6 and 1.6.1.
			return 5;
		} else if (version.equals("1.6.3")) {
			return 6;
		} else if (version.equals("1.6.4")) {
			return 7;
		} else if (version.equals("1.6.5")) {
			return 8;
		} else if (version.equals("1.6.6")) {
			return 9;
		} else if (version.equals("1.7")) {
			return 10;
		} else if (version.equals("1.7_01")) {
			return 11;
		} else if (version.equals("1.7.2")) {
			return 12;
		} else if (version.equals("1.7.3")) {
			return 13;
		} else if (version.equals("1.8")) {
			return 14;
		} else if (version.equals("1.8.1")) {
			// Latest supported version. 
			return 15;
		} else {
			return -1;
		}
	}

	/**
	 * She thinks she will die soon, but there's still a long way to go.
	 */
	public static void registerEvent(JavaPlugin plugin, String type,
			Listener listener, Priority priority) {
		plugin.getServer().getPluginManager().registerEvent(Type.valueOf(type), 
				listener, priority, plugin);
	}

	public static void registerEventSafely(JavaPlugin plugin, String type,
			Listener listener, Priority priority) {
		try {
			registerEvent(plugin, type, listener, priority);
		} catch (Exception ex) {
			log.warning("BV: Could not register \"" + type + "\" for MultiWorld.");
			log.warning("BV: Perhaps you should use the newest Craftbukkit build?");
		}
	}

	/**
	 * You still have to do self-check for the Bukkit's version for no warnings.
	 *
	 * Safely registers an event.
	 *
	 * @param type Type enum to register (ex. "PLAYER_ITEM", "PLAYER_INTERACT")
	 */
	public static void registerEventSafely(JavaPlugin plugin, String type,
			Listener listener) {
		registerEventSafely(plugin, type, listener, Priority.Normal);
	}
}
