package pl.moresteck.multiworld.listeners;

import org.bukkit.event.world.WorldEvent;
import org.bukkit.event.world.WorldListener;

import pl.moresteck.multiworld.MultiWorld;

public class worldlistener {

	public class v12 extends WorldListener {
		public void onWorldLoaded(WorldEvent e) {
			if (e.getWorld() == MultiWorld.server.getWorlds().get(0)) {
				MultiWorld.instance.init();
			}
		}
	}

	public class v13 extends WorldListener {
		public void onWorldLoad(org.bukkit.event.world.WorldLoadEvent e) {
			if (e.getWorld() == MultiWorld.server.getWorlds().get(0)) {
				MultiWorld.instance.init();
			}
		}
	}
}
