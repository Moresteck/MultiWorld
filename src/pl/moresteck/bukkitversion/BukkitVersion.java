package pl.moresteck.bukkitversion;

import net.minecraft.server.MinecraftServer;

import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.moresteck.multiworld.MultiWorld;

public class BukkitVersion {
	private static double addon_version = 0.5;

	private String version;
	private JavaPlugin plugin;

	public BukkitVersion(JavaPlugin plugin) {
		this.plugin = plugin;
		this.setupVersion((CraftServer) plugin.getServer());
	}

	public static double getBVVersion() {
		return addon_version;
	}

	private void setupVersion(final CraftServer server) {
		try {
			this.version = new VersionParser().parseVersion(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getVersion() {
		return this.version;
	}

	public String getBukkitVersion() {
		return plugin.getServer().getVersion();
	}

	/**
	 * Says if the Bukkit version is higher than 1.1-R4.
	 *
	 * @return Version higher than 1.1-R4
	 */
	public boolean isBukkitNewSystem() {
		return getVersionId() > 20 ? true : 
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
	public int getVersionId() {
		if (version.equals("b1.1")) {
			// It may be b1.1, b1.1_02 or b1.2.
			return 0;
		} else if (version.equals("b1.2_01")) {
			// b1.2_02 vanilla server never existed.
			return 1;
		} else if (version.equals("b1.3")) {
			return 2;
		} else if (version.equals("b1.4")) {
			return 3;
		} else if (version.equals("b1.4_01")) {
			return 4;
		} else if (version.equals("b1.5_02")) {
			// No Bukkit for b1.5 and b1.5_01.
			return 5;
		} else if (version.equals("b1.6.2")) {
			// No Bukkit for b1.6 and b1.6.1.
			return 6;
		} else if (version.equals("b1.6.3")) {
			return 7;
		} else if (version.equals("b1.6.4")) {
			return 8;
		} else if (version.equals("b1.6.5")) {
			return 9;
		} else if (version.equals("b1.6.6")) {
			return 10;
		} else if (version.equals("b1.7")) {
			return 11;
		} else if (version.equals("b1.7_01")) {
			return 12;
		} else if (version.equals("b1.7.2")) {
			return 13;
		} else if (version.equals("b1.7.3")) {
			return 14;
		} else if (version.equals("b1.8")) {
			return 15;
		} else if (version.equals("b1.8.1")) {
			return 16;
		} else if (version.equals("b1.9")) {
			return 17;
		} else if (version.equals("1.0.0")) {
			return 18;
		} else if (version.equals("1.0.1")) {
			return 19;
		} else if (version.equals("1.1")) {
			return 20;
		} else {
			// TODO check new compatibility.
			return 21;
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
	public void registerEvent(String type, Listener listener, String priority) {
		if (isBukkitNewSystem()) {
			this.plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		} else {
			this.plugin.getServer().getPluginManager().registerEvent(org.bukkit.event.Event.Type.valueOf(type), 
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
	public void registerEventSafely(String type, Listener listener, String priority) {
		try {
			this.registerEvent(type, listener, priority);
		} catch (Exception ex) {
			MultiWorld.log.info("BV: Could not register \"" + type + "\" for " + this.plugin.getDescription().getName());
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
	public void registerEventSafely(String type, Listener listener) {
		registerEventSafely(type, listener, "Normal");
	}

	private static class VersionParser {

		/**
		 * Gives the version number from b1.1 to 1.1.
		 * <br />
		 * For higher versions use {@link BukkitVersion.getVersionId()}
		 * or {@link BukkitVersion.isVersionHigh()}
		 *
		 * @param server Server instance
		 * @return Formatted version
		 */
		public String parseVersion(CraftServer server) {
			String bver = server.getVersion();
			if (bver.equals("1.1") || bver.equals("1.2_01")) {
				// "1.1" may indicate b1.1, b1.1_02 or even b1.2!
				// b1.2_02 vanilla server never existed.
				return "b" + bver;
			} else {
				String[] part = bver.split(" ");
				String version = part[2].replace(")", "");
				// Detects b1.9-pre5.
				if (version.equals("Beta 1.9 Prerelease 5")) {
					return "b1.9";
				}
				if (version.equals("1.3")) {
					// Detects b1.4_01 and 1.3
					try {
						@SuppressWarnings("unused")
						MinecraftServer minecraftserver = server.getHandle().server;
						return "b1.4_01";
					} catch (Exception ex) {
						return "b1.3";
					}
				}
				// Detects most of Beta versions.
				if (version.equals("1.2_01") || version.equals("1.4") || 
						version.equals("1.5_02") || version.equals("1.6.3") || 
						version.equals("1.6.5") || version.equals("1.6.6") || 
						version.equals("1.7") || version.equals("1.7_01") || 
						version.equals("1.7.3") || version.equals("1.8.1")) {
					return "b" + version;
				}
				// Detects b1.6.2, b1.6.4, b1.7.2 and b1.8
				try {
					server.createWorld("world", Environment.NORMAL);
					return "b" + version;
				} catch (Exception ex) {
					return version;
				}
			}
		}
	}
}
