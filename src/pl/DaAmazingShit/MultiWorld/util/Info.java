package pl.DaAmazingShit.MultiWorld.util;

public class Info {
	
	public static String line1(String worldname) {
		return "Worldname: §e" + worldname;
	}
	
	public static String line2(String worldname, String type) {
		if (type.equalsIgnoreCase("nether")) {
			return "Environment: §c" + type;
		}
		if (type.equalsIgnoreCase("normal")) {
			return "Environment: §a" + type;
		}
		else {
			return null;
		}
	}
	
	public static String line3(String num) {
		return "Entities in: §a" + num;
	}
	
	public static String line4(String players) {
		return "Players in: " + players;
	}
	
}
