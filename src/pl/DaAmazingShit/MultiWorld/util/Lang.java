package pl.DaAmazingShit.MultiWorld.util;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.util.config.Configuration;

public class Lang {
	
	public static File langFile = new File("plugins/MultiWorld", "lang.yml");
	public static Configuration lang = new Configuration(langFile);
	
	public static void update() {
		helpCommand   = lang.getString("command.help", helpCommand);
		createCommand = lang.getString("command.create", createCommand);
		removeCommand = lang.getString("command.remove", removeCommand);
		whoCommand    = lang.getString("command.who", whoCommand);
		whereCommand  = lang.getString("command.where", whereCommand);
		importCommand = lang.getString("command.import", importCommand);
		clearCommand  = lang.getString("command.clear", clearCommand);
		infoCommand   = lang.getString("command.info", infoCommand);
	}
	
	public static void startup() {
		// load
	}
	
	
	public static String prefix        = ChatColor.GREEN + "MultiWorld: " + ChatColor.WHITE;
	public static String prefixWrong   = ChatColor.RED + "MultiWorld: " + ChatColor.WHITE;
	public static String helpCommand   = "multiworld";
	public static String createCommand = "createworld <WORLD> <ENV>";
	public static String removeCommand = "removeworld <WORLD>";
	public static String whoCommand    = "whoisin <WORLD>";
	public static String importCommand = "importworld <WORLD> <ENV>";
	public static String infoCommand   = "worldinfo <WORLD>";
	public static String whereCommand  = "whereis [PLAYER]";
	public static String clearCommand  = "clearworld [WORLD]";
}
