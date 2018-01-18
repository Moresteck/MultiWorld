package pl.amazingshit.mw;

import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.event.Event.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.amazingshit.mw.listeners.*;
import pl.amazingshit.mw.managers.*;

public class MultiWorld extends JavaPlugin {

	public static Logger log = Logger.getLogger("Minecraft");

	public static Plugin instance;
	public static Boolean permissionsEnabled = false;
	public static Boolean someutilsEnabled = false;
	public static Boolean mobarenaEnabled = false;
	public void onDisable() {}

	@Override
	public void onEnable() {
		instance = this;
		log.info("MultiWorld v" + this.getDescription().getVersion() + " enabled.");
		
		loadWorlds();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.CREATURE_SPAWN,  new Entities(), Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DAMAGED,  new Entities(), Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_EXPLODE,  new Entities(), Priority.Highest, this);
		
		pm.registerEvent(Type.PLUGIN_ENABLE,   new Plugins(),  Priority.Monitor, this);
		
		pm.registerEvent(Type.PLAYER_QUIT,     new Players(),  Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_JOIN,     new Players(),  Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_TELEPORT, new Players(),  Priority.Monitor, this);
	}

	public static void loadWorlds() {
		World w = instance.getServer().getWorld("world");
		if (!ConfigWorld.exists("world") || ConfigWorld.getWorldStringList().isEmpty()) {
			ConfigWorld.add("world", World.Environment.NORMAL, true, true, true, true, "true", true, true, w.getId());
		}
		for (WorldManager world: ConfigWorld.getWorldList()) {
			world.setup();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdalias, String[] args) {
		CommandManager manager = new CommandManager(sender, cmdalias, args);
		manager.handleCommand();
		return true;
	}
}