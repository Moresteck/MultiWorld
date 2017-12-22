package pl.DaAmazingShit.MultiWorld.listeners;

import java.util.HashMap;
import org.bukkit.World;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MWListener extends PlayerListener {
	
	public static HashMap<String, World> whereIsPlayer     = new HashMap<String, World>();
	public static HashMap<String, String> worldsAndPlayers = new HashMap<String, String>();
	
	@Override
	public void onPlayerTeleport(PlayerMoveEvent e) {
		World from = e.getFrom().getWorld();
		World to   = e.getTo().getWorld();
		
		if (from.getName() != to.getName()) {
			whereIsPlayer.remove(e.getPlayer().getName());
			whereIsPlayer.put(e.getPlayer().getName(), to);
			worldsAndPlayers.remove(from.getName(), e.getPlayer().getName());
			worldsAndPlayers.put(to.getName(), e.getPlayer().getName());
		}
	}
	
	@Override
	public void onPlayerJoin(PlayerEvent e) {
		World world = e.getPlayer().getWorld();
		
		whereIsPlayer.put(e.getPlayer().getName(), world);
		worldsAndPlayers.put(world.getName(), e.getPlayer().getName());
	}
	
	@Override
	public void onPlayerQuit(PlayerEvent e) {
		World world = e.getPlayer().getWorld();
		
		whereIsPlayer.remove(e.getPlayer().getName());
		worldsAndPlayers.remove(world.getName(), e.getPlayer().getName());
	}
}
