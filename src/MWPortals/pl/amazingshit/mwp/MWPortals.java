package MWPortals.pl.amazingshit.mwp;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import MWPortals.pl.amazingshit.mwp.listeners.BlockListener12;
import MWPortals.pl.amazingshit.mwp.listeners.PortalListener;
import MWPortals.pl.amazingshit.mwp.managers.CommandManager;
import MWPortals.pl.amazingshit.mwp.managers.PortalManager;

public class MWPortals extends JavaPlugin {

	public static Logger log = Logger.getLogger("Minecraft");

	public static boolean enabled = false;
	public static Plugin instance = null;

	@Override
	public void onEnable() {
		instance = this;
		List<String> pllist = new LinkedList<String>();
		for (Plugin p: this.getServer().getPluginManager().getPlugins()) {
			pllist.add(p.getDescription().getName());
		}
		if (pllist.contains("MultiWorld")) {
			enabled = true;
		}
		if (enabled == false) {
			log.info("[MWPortals] MultiWorld hasn't been detected! Disabling!");
			this.getServer().getPluginManager().disablePlugin(this);
		}
		
		if (!PortalManager.exists()) {
			PortalManager.defaultConfig();
		}
		log.info("MWPortals enabled successfully!");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.BLOCK_INTERACT, new BlockListener12(), Priority.Monitor, this);
		pm.registerEvent(Type.BLOCK_RIGHTCLICKED, new BlockListener12(), Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_MOVE, new PortalListener(), Priority.Monitor, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!enabled) {
			return true;
		}
		CommandManager.handleCommand(sender, args, alias);
		return true;
	}

	public void onDisable() {}
}