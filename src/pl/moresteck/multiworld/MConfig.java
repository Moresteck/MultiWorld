package pl.moresteck.multiworld;

import java.io.File;

import pl.moresteck.bukkitversion.Config;

public class MConfig {
	private static Config config = new Config(new File("plugins/MultiWorld", "config.yml"));

	public static boolean debug() {
		return get("debug-messages");
	}

	public static boolean historyEnabled() {
		return get("history-enabled");
	}

	private static boolean get(String node) {
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
		config.set("debug-messages", false);
		config.set("enable-history", true);
		config.save();
	}
}
