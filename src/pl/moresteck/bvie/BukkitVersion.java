package pl.moresteck.bvie;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.moresteck.multiworld.MultiWorld;

public class BukkitVersion {
	// We'll always use this, not the Plugin Edition.
	public static String plugin_version = "0.4 IE (Integrated Edition)";
	private static String version;

	// We aren't using String here because some other plugin could
	// fake the version number. This method isn't 100% secure tho...
	public static void setupVersion(final CraftServer server) {
		BukkitVersion.version = VersionParser.parseVersion(server.getVersion());
	}

	public static String getVersion() {
		return BukkitVersion.version;
	}

	public static String getBukkitVersion() {
		return MultiWorld.server.getVersion();
	}

	/**
	 * Says if the Bukkit version is higher than 1.1-R4.
	 *
	 * @return Version higher than 1.1-R4
	 */
	public static boolean isVersionHigh() {
		return getVersionId() > 19 ? true : 
		getBukkitVersion().startsWith("git-Bukkit-1.1-R4") || 
		getBukkitVersion().startsWith("git-Bukkit-1.1-R5") || 
		getBukkitVersion().startsWith("git-Bukkit-1.1-R6");
	}

	/**
	 * Gets the version number. <br />
	 * Detects versions from b1.1 to 1.1. <br />
	 * ID 20 is higher than 1.1.
	 *
	 * @return Version id starting from 0
	 */
	public static int getVersionId() {
		if (version.equals("b1.1")) {
			// It may be b1.1, b1.1_02 or b1.2.
			return 0;
		} else if (version.equals("b1.2_01")) {
			// b1.2_02 vanilla server never existed.
			return 1;
		} else if (version.equals("b1.3")) {
			return 2;
		} else if (version.equals("b1.4")) {
			// Cannot recognize b1.4_01 Bukkit.
			return 3;
		} else if (version.equals("b1.5_02")) {
			// No Bukkit for b1.5 and b1.5_01.
			return 4;
		} else if (version.equals("b1.6.2")) {
			// No Bukkit for b1.6 and b1.6.1.
			return 5;
		} else if (version.equals("b1.6.3")) {
			return 6;
		} else if (version.equals("b1.6.4")) {
			return 7;
		} else if (version.equals("b1.6.5")) {
			return 8;
		} else if (version.equals("b1.6.6")) {
			return 9;
		} else if (version.equals("b1.7")) {
			return 10;
		} else if (version.equals("b1.7_01")) {
			return 11;
		} else if (version.equals("b1.7.2")) {
			return 12;
		} else if (version.equals("b1.7.3")) {
			return 13;
		} else if (version.equals("b1.8")) {
			return 14;
		} else if (version.equals("b1.8.1")) {
			return 15;
		} else if (version.equals("b1.9")) {
			return 16;
		} else if (version.equals("1.0.0")) {
			return 17;
		} else if (version.equals("1.0.1")) {
			return 18;
		} else if (version.equals("1.1")) {
			return 19;
		} else {
			// TODO check new compatibility.
			return 20;
		}
	}

	/**
	 * Registers an event, but may result in an NPE.
	 * <br />
	 * You don't have to specify the priority and type if your version is 1.1-R4 or higher.
	 *
	 * @param type Type enum to register (e.g. "PLAYER_ITEM", "PLAYER_INTERACT")
	 * @param priority Priority to register (Lowest, Low, Normal, Monitor, High, Highest)
	 */
	public static void registerEvent(JavaPlugin plugin, String type,
			Listener listener, String priority) {
		if (isVersionHigh()) {
			plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		} else {
			plugin.getServer().getPluginManager().registerEvent(org.bukkit.event.Event.Type.valueOf(type), 
					listener, org.bukkit.event.Event.Priority.valueOf(priority), plugin);
		}
	}

	/**
	 * You still have to do self-check for the Bukkit's version for no warnings.
	 * <br />
	 * Safely registers an event with specified Priority. <br />
	 * You don't have to specify the priority and type if your version is 1.1-R4 or higher.
	 *
	 * @param type Type enum to register (e.g. "PLAYER_ITEM", "PLAYER_INTERACT")
	 * @param priority Priority to register (Lowest, Low, Normal, Monitor, High, Highest)
	 */
	public static void registerEventSafely(JavaPlugin plugin, String type,
			Listener listener, String priority) {
		try {
			registerEvent(plugin, type, listener, priority);
		} catch (Exception ex) {
			MultiWorld.log.info("BV: Could not register \"" + type + "\" for MultiWorld.");
			MultiWorld.log.info("BV: Perhaps you should use the newest Craftbukkit build?");
		}
	}

	/**
	 * You still have to do self-check for the Bukkit's version for no warnings.
	 * <br />
	 * Safely registers an event.
	 *
	 * @param type Type enum to register (e.g. "PLAYER_ITEM", "PLAYER_INTERACT")
	 */
	public static void registerEventSafely(JavaPlugin plugin, String type,
			Listener listener) {
		registerEventSafely(plugin, type, listener, "Normal");
	}
}
