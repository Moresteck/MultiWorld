package pl.amazingshit.mw.listeners;

import org.bukkit.event.server.*;
import org.bukkit.plugin.Plugin;

import pl.amazingshit.mw.mw;

public class Plugins extends ServerListener {

	@Override public void onPluginEnabled(PluginEvent e) {
		Plugin plugin = e.getPlugin();
		if (plugin.getDescription().getName().equalsIgnoreCase("Permissions")) {
			mw.permissionsEnabled = true;
			mw.log.info("[MultiWorld] Linked with Permissions.");
		}
		if (plugin.getDescription().getName().equalsIgnoreCase("MobArena")) {
			mw.mobarenaEnabled = true;
			mw.log.info("[MultiWorld] Linked with MobArena.");
		}
		if (plugin.getDescription().getName().equalsIgnoreCase("SomeUtils")) {
			mw.someutilsEnabled = true;
			mw.log.info("[MultiWorld] Linked with SomeUtils.");
		}
	}
}