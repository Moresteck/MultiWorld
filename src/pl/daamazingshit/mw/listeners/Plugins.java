package pl.daamazingshit.mw.listeners;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import pl.daamazingshit.mw.mw;

public class Plugins extends ServerListener {

	@Override public void onPluginEnable(PluginEnableEvent e) {
		Plugin plugin = e.getPlugin();
		if (plugin.getDescription().getName().equalsIgnoreCase("Permissions")) {
			mw.permissionsEnabled = true;
			mw.log.info("[MultiWorld] Linked with Permissions.");
		}
		if (plugin.getDescription().getName().equalsIgnoreCase("MobArena")) {
			mw.mobarenaEnabled = true;
			mw.log.info("[MultiWorld] Linked with MobArena.");
		}
	}
}