package pl.moresteck.multiworld.listeners;

import org.bukkit.event.world.WorldListener;

import pl.moresteck.multiworld.MultiWorld;

public class worldlistener extends WorldListener {

	public void onWorldLoad(org.bukkit.event.world.WorldLoadEvent e) {
		if (e.getWorld() == MultiWorld.server.getWorlds().get(0)) {
			MultiWorld.instance.load();
		}
	}
}
