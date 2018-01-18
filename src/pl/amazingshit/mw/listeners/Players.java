package pl.amazingshit.mw.listeners;

import org.bukkit.event.player.*;

import pl.amazingshit.mw.managers.WorldManager;

public class Players extends PlayerListener {

	@Override public void onPlayerJoin(PlayerEvent e) {
		WorldManager wm = new WorldManager(e.getPlayer().getWorld().getName());
		wm.players.add(e.getPlayer());
	}

	@Override public void onPlayerQuit(PlayerEvent e) {
		WorldManager wm = new WorldManager(e.getPlayer().getWorld().getName());
		wm.players.remove(e.getPlayer());
	}

	@Override public void onPlayerTeleport(PlayerMoveEvent e) {
		if (e.isCancelled()) {
			return;
		}
		WorldManager from = new WorldManager(e.getFrom().getWorld().getName());
		from.players.remove(e.getPlayer());
		WorldManager wm = new WorldManager(e.getTo().getWorld().getName());
		wm.players.add(e.getPlayer());
	}
}