package pl.moresteck.bvnpe;

import org.bukkit.Server;

public class VersionParser {

	public static String parseVersion(Server server) {
		return parseVersion(server.getVersion());
	}

	/**
	 * Gives the version number from b1.1 to 1.1.
	 * <br />
	 * For higher versions use {@link BukkitVersion.getVersionId()}
	 * or {@link BukkitVersion.isVersionHigh()}
	 *
	 * @param bver Version from Bukkit.getVersion()
	 * @return Formatted version
	 */
	public static String parseVersion(String bver) {
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
			// Detects 1.0.0 - 1.1.
			if (version.equals("1.0.0") || version.equals("1.0.1") || 
					version.equals("1.1")) {
				return version;
			}
			return "b" + version;
		}
	}

	public static int getVersionId() {
		return BukkitVersion.getVersionId();
	}
}
