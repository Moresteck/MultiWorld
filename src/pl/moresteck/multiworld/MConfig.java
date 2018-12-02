package pl.moresteck.multiworld;

import java.io.File;

import bukkit.util.config.Configuration;

public class MConfig {
	private static Configuration config = new Configuration(new File("plugins/MultiWorld", "config.yml"));

	public static boolean debug() {
		config.load();
		String s = config.getString("debug-messages", null);
		boolean debug;
		if (s == null) {
			defaultSetup();
			debug = false;
		} else {
			try {
				debug = Boolean.parseBoolean(s);
			} catch (Exception ex) {
				MultiWorld.log.info("[MultiWorld] ERROR at 'plugins/MultiWorld/config.yml': 'debug-messages' IS NOT a boolean value.");
				debug = false;
			}
		}
		return debug;
	}

	public static boolean historyEnabled() {
		config.load();
		String b = config.getString("enable-history", null);
		boolean history;
		if (b == null) {
			defaultSetup();
			history = true;
		} else {
			try {
				history = Boolean.parseBoolean(b);
			} catch (Exception ex) {
				MultiWorld.log.info("[MultiWorld] ERROR at 'plugins/MultiWorld/config.yml': 'debug-messages' IS NOT a boolean value.");
				history = true;
			}
		}
		return history;
	}

	protected static void defaultSetup() {
		config.load();
		config.setProperty("debug-messages", false);
		config.setProperty("enable-history", true);
		config.save();
	}
}
