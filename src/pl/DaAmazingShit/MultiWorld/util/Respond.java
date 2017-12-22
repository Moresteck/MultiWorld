package pl.DaAmazingShit.MultiWorld.util;

import org.bukkit.World.Environment;

public class Respond {
	
	public static String unknownError       = "Undefined error. See console.";
	public static String notEnoughArguments = "Not enough arguments!";
	public static String notRecognised      = "World could not be recognised!";
	public static String createdSuccess     = "World §e<WORLD>§f created successfully!";
	public static String removedSuccess     = "World §e<WORLD>§f removed successfully!";
	public static String importedSuccess    = "World §e<WORLD>§f imported successfully!";
	public static String who                = "§e<WORLD>§f has §b<NUM>§f player(s): <PLAYERS>";
	public static String info               = "Worldname: §e<WORLD>§f+Environment: §<CC><WORLD>§f+Entities on the ground: §a<NUM>§f+Players in (§b<NUM>§f): <PLAYERS>";
	public static String where              = "§e<PLAYER>§f is in §e<WORLD>§f.";
	public static String errorImport        = "World §e<WORLD>§f could not be imported! See console.";
	public static String errorRemove        = "World §e<WORLD>§f could not be removed! See console.";
	public static String errorWhere         = "An error occured. Probably the player does not exists?";
	public static String clearSuccess       = "Cleared §b<NUM>§f entities from §e<WORLD>§f.";
	//public static String help               = " §aMultiWorld help (page 1)+ §e/createworld§f <world> <environment>+ §e/importworld§f <world> <environment>+ §e/whoisin§f [world]+ §e/whereis§f <world>+ §e/clearworld§f [world]+ §e/worldinfo§f [world]+ §e/worldtp§f <world> [player]+ See §e/multiworld 2§f for more help.";
	//public static String help2              = " §aMultiWorld help (page 2)+ §eEnvironments can be: §anormal §for §cnether§f.+ Plugin created by §bDaAmazingShit§f, §92017§f.+ Version: §6" + MultiWorldMain.pluginVersion + "+ §fServer version: §c" + MultiWorldMain.version;
	
	public static Environment getEnv(String env) {
		if (env.equalsIgnoreCase("normal")) {
			return Environment.NORMAL;
		}
		if (env.equalsIgnoreCase("nether")) {
			return Environment.NETHER;
		}
		else {
			return null;
		}
	}
	
	public static String wrongEnvironment() {
		return "Could not recognise environment";
	}
	
	public static class Error {
		
		public static String unknown() {
			return "Undefined error. See console.";
		}
		
		public static String notEnoughArguments() {
			return "Not enough arguments!";
		}
		
		public static String notRecognised() {
			return "World could not be recognised!";
		}
		
		public static String imported(String world) {
			return "World §e" + world + "§f could not be imported! See console.";
		}
		
		public static String create(String world) {
			return "World §e" + world + "§f could not be created! See console.";
		}
		
		public static String remove(String world) {
			return "World §e" + world + "§f could not be removed! See console.";
		}
	}

	public static String who(String playersInWorld, String num, String world) {
		return "§e" + world + "§f has §b" + num + "§f player(s): " + playersInWorld;
	}

	public static String where(String name, String world) {
		return "§e" + name + "§f is in §e" + world + "§f.";
	}

	public static String clear(String num, String world) {
		return "Cleared §b" + num + "§f entities from §e" + world + "§f.";
	}

	public static String imported(String world) {
		return "World §e" + world + "§f imported successfully!";
	}
	
	public static String created(String world) {
		return "World §e" + world + "§f created successfully!";
	}
	
	public static String removed(String world) {
		return "World §e" + world + "§f removed successfully!";
	}
}
