package pl.moresteck.multiworld;

import java.io.File;

import bukkit.util.config.Configuration;

public class MConfig {
	private static Configuration config = new Configuration(new File("plugins/MultiWorld", "config.yml"));

	public static boolean debug() {
		return get("debug-messages");
	}

	public static boolean historyEnabled() {
		return get("history-enabled");
	}

	private static boolean get(String node) {
		config.load();
		String b = config.getString(node, null);
		boolean get;
		if (b == null) {
			defaultSetup();
			get = true;
		} else {
			try {
				get = Boolean.parseBoolean(b);
			} catch (Exception ex) {
				MultiWorld.log.info("[MultiWorld] ERROR at 'plugins/MultiWorld/config.yml': '" + node + "' IS NOT a boolean value.");
				get = false;
			}
		}
		return get;
	}

	protected static void defaultSetup() {
		config.load();
		config.setProperty("debug-messages", false);
		config.setProperty("enable-history", true);
		config.save();
	}
}
