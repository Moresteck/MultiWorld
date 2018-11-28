package pl.moresteck.bvnpe;

import org.bukkit.Server;

public class VersionParser {

	public static String parseVersion(Server server) {
		return parseVersion(server.getVersion());
	}

	public static String parseVersion(String bver) {
		if (bver.equals("1.1") || bver.equals("1.2_01")) {
			// "1.1" may indicate b1.1, b1.1_02 or even b1.2!
			// b1.2_02 vanilla server never existed.
			return "b" + bver;
		} else {
			String[] part = bver.split(" ");
			String version = part[2].replace(")", "");
			// Detects 1.0.0 - 1.1.
			if (version.equals("1.0.0") || version.equals("1.0.1") ||
					version.equals("1.1")) {
				return version;
			}
			return "b" + version;
		}
	}
}
