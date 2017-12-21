package pl.DaAmazingShit.MultiWorld;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import pl.DaAmazingShit.MultiWorld.listeners.MWListener;
import pl.DaAmazingShit.MultiWorld.util.Util;

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
		if (!configFile.exists()) {
			Util.firstStartup();
		}
		Util.loadWorlds();
	}
	
	@Override
	public void onDisable() {
		Util.saveWorlds();
	}
	
	private static void registerEvents() {
		
		PluginManager pm = staticServer.getPluginManager();
		pm.registerEvent(Type.PLAYER_MOVE, new MWListener(), Priority.Monitor, mwm);
		pm.registerEvent(Type.PLAYER_COMMAND, new MWListener(), Priority.Monitor, mwm);
		pm.registerEvent(Type.PLAYER_TELEPORT, new MWListener(), Priority.Monitor, mwm);
	}
}
