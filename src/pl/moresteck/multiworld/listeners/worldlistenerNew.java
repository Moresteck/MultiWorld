package pl.moresteck.multiworld.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import pl.moresteck.multiworld.MultiWorld;

public class worldlistenerNew implements Listener {

	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		if (e.getWorld() == MultiWorld.server.getWorlds().get(0)) {
			MultiWorld.instance.load();
		}
	}
}
