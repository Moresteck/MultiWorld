package pl.DaAmazingShit.MultiWorld;

import java.io.File;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import pl.DaAmazingShit.MultiWorld.listeners.MWListener;
import pl.DaAmazingShit.MultiWorld.util.Util;

public class MultiWorldMain extends JavaPlugin {
	
	//public static Server staticServer;
	
	// Configuration & stuff
	public static File configFile = new File("plugins/MultiWorld", "configuration.yml");
	public static File permFile = new File("plugins/MultiWorld", "perms.yml");
	
	public static Configuration permissions = new Configuration(permFile);
	public static Configuration config = new Configuration(configFile);
	
	public static String version;
	public static String pluginVersion = "1.0-beta3";
	
	@Override
	public void onEnable() {
		System.out.println("Enabling MultiWorld...");
		try {
			version = this.getServer().getVersion();
			version.replaceAll(CraftServer.class.getPackage().getImplementationVersion(), "");
			version.replace(" (MC: ", "");
			version.replace(")", "");
			registerEvents();
			if (!configFile.exists()) {
				Util.firstStartup();
			}
			Util.loadWorlds();
		}
		catch (Exception ex) {
			System.out.println("Could not enable MultiWorld! This is a bug!");
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		Util.saveWorlds();
	}
	
	private void registerEvents() {
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_MOVE, new MWListener(), Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_COMMAND, new MWListener(), Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_TELEPORT, new MWListener(), Priority.Monitor, this);
	}
}
