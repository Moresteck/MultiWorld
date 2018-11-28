package pl.moresteck.multiworld.listeners;

import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;

import pl.moresteck.multiworld.Perm;

public class pluginlistener {

	public class v12 extends ServerListener {
		public void onPluginEnabled(PluginEvent e) {
			if (e.getPlugin().getDescription().getName().equals("Permissions")) {
				new Perm().setPermissions(e.getPlugin());
			}
		}
	}

	public class v13 extends ServerListener {
		public void onPluginEnable(org.bukkit.event.server.PluginEnableEvent e) {
			if (e.getPlugin().getDescription().getName().equals("Permissions")) {
				new Perm().setPermissions(e.getPlugin());
			}
		}
	}
}
