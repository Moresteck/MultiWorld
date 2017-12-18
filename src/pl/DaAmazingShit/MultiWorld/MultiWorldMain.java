package pl.DaAmazingShit.MultiWorld;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import pl.DaAmazingShit.MultiWorld.listeners.MWListener;

public class MultiWorldMain extends JavaPlugin {
	
	private static MultiWorldMain mwm;
	public static Server staticServer = mwm.getServer();
	public static File permFile = new File("plugins/MultiWorld", "perms.yml");
	public static Configuration permissions = new Configuration(permFile);
	public static File configFile = new File("plugins/MultiWorld", "configuration.yml");
	public static Configuration config = new Configuration(configFile);
	
	@Override
	public void onEnable() {
		registerEvents();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private static void registerEvents() {
		
		PluginManager pm = staticServer.getPluginManager();
		pm.registerEvent(Type.PLAYER_MOVE, new MWListener(), Priority.Monitor, mwm);
		
	}
	
	@Override
	public boolean onCommand(CommandSender send, Command cmd, String str, String[] args) {
		if (cmd.getName().equalsIgnoreCase("multiworld")) {
			
			send.sendMessage(ChatColor.BLUE + " MultiWorld help:");
			send.sendMessage("  /createworld <name> <environment> " + ChatColor.YELLOW + "Creates new world.");
			return true;
		}
		return false;
	}
}
