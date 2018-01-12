package pl.amazingshit.mw;

import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.amazingshit.mw.listeners.Entities;
import pl.amazingshit.mw.listeners.Plugins;
import pl.amazingshit.mw.managers.CommandManager;
import pl.amazingshit.mw.managers.ConfigWorld;
import pl.amazingshit.mw.managers.WorldManager;

public class MultiWorld extends JavaPlugin {

	public static Logger log = Logger.getLogger("Minecraft");

	public static Plugin instance;
	public static Boolean permissionsEnabled = false;
	public static Boolean mwportalsEnabled = false;
	public static Boolean mobarenaEnabled = false;
	public void onDisable() {}

	@Override
	public void onEnable() {
		instance = this;
		log.info("MultiWorld v" + this.getDescription().getVersion() + " enabled.");
		
		loadWorlds();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.CREATURE_SPAWN, new Entities(), Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DAMAGE,  new Entities(), Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_EXPLODE, new Entities(), Priority.Highest, this);
		
		pm.registerEvent(Type.PLUGIN_ENABLE,  new Plugins(),  Priority.Monitor, this);
	}

	public static void loadWorlds() {
		World w = instance.getServer().getWorld("world");
		if (!ConfigWorld.exists("world") || ConfigWorld.getWorldStringList().isEmpty()) {
			ConfigWorld.add("world", Environment.NORMAL, true, true, true, true, "true", true, true, true, w.getId());
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