package pl.moresteck.bvnpe;

import org.bukkit.Server;

public class VersionParser {

	public static String parseVersion(Server server) {
		return parseVersion(server.getVersion());
	}

	public static String parseVersion(String bver) {
		if (bver.equals("1.1") || bver.equals("1.2_01")) {
			// "1.1" may indicate 1.1, 1.1_02 or even 1.2!
			// 1.2_02 vanilla server never existed.
			return bver;
		} else {
			String[] part = bver.split(" ");
			String version = part[2].replace(")", "");
			return version;
		}
	}
}
