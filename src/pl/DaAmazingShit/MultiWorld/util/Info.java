package pl.DaAmazingShit.MultiWorld.util;

public class Info {
	
	public static String line1 = "Worldname: §e<WORLD>§f";
	public static String line2 = "";
	public static String line3 = "";
	public static String line4 = "";
	
	public static String line1(String worldname) {
		return "Worldname: §e" + worldname;
	}
	
	public static String line2(String worldname, String type) {
		if (type.equalsIgnoreCase("nether")) {
			return "Environment: §c" + worldname;
		}
		if (type.equalsIgnoreCase("normal")) {
			return "Environment: §a" + worldname;
		}
		else {
			return null;
		}
	}
	
	public static String line3(String worldname) {
		return "Worldname: §e" + worldname;
	}
	
	public static String line4(String worldname) {
		return "Worldname: §e" + worldname;
	}
	
}
