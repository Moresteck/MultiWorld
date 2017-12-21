package pl.DaAmazingShit.MultiWorld;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import pl.DaAmazingShit.MultiWorld.listeners.MWListener;
import pl.DaAmazingShit.MultiWorld.util.Lang;

public class MultiWorldMain extends JavaPlugin {
	
	public static MultiWorldMain mwm;
	public static Server staticServer = mwm.getServer();
	
	// Configuration & stuff
	public static File configFile = new File("plugins/MultiWorld", "configuration.yml");
	public static File permFile = new File("plugins/MultiWorld", "perms.yml");
	
	public static Configuration permissions = new Configuration(permFile);
	public static Configuration config = new Configuration(configFile);
	
	public static String version = staticServer.getVersion();
	
	@Override
	public void onEnable() {
		version.replaceAll(CraftServer.class.getPackage().getImplementationVersion(), "");
		version.replaceAll(" (MC: ", "");
		version.replaceAll(")", "");
		registerEvents();
	}
	
	@Override
	public void onDisable() {
		mwm.getServer().getVersion();
	}
	
	private static void registerEvents() {
		
		PluginManager pm = staticServer.getPluginManager();
		pm.registerEvent(Type.PLAYER_MOVE, new MWListener(), Priority.Monitor, mwm);
		
	}
}
