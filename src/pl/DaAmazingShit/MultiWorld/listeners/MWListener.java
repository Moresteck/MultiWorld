package pl.DaAmazingShit.MultiWorld.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import pl.DaAmazingShit.MultiWorld.MultiWorldMain;

public class MWListener extends PlayerListener {
	
	public static HashMap<String, World>   whereIsPlayer       = new HashMap<String, World>();
	public static HashMap<String, String>  worldsAndPlayers    = new HashMap<String, String>();
	public static HashMap<String, Integer> worldsPlayerInteger = new HashMap<String, Integer>();
	
	@Override
	public void onPlayerTeleport(PlayerMoveEvent e) {
		World from = e.getFrom().getWorld();
		World to   = e.getTo().getWorld();
		
		if (from.getName() != to.getName()) {
			whereIsPlayer.remove(e.getPlayer().getName());
			whereIsPlayer.put(e.getPlayer().getName(), to);
			worldsAndPlayers.remove(from.getName(), e.getPlayer().getName());
			worldsAndPlayers.put(to.getName(), e.getPlayer().getName());
			worldsPlayerInteger.remove(from.getName(), e.getPlayer().getName());
			worldsPlayerInteger.put(to.getName(), 1);
		}
	}
	
	@Override
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		
		String mainworld = null;
		mainworld = MultiWorldMain.config.getString("main", mainworld);
		
		int x    = 0;
		int y    = 0;
		int z    = 0;
		float pi = 0;
		float ya = 0;
		x  = MultiWorldMain.config.getInt("worlds."+mainworld+".spawn.x", x);
		y  = MultiWorldMain.config.getInt("worlds."+mainworld+".spawn.y", y);
		z  = MultiWorldMain.config.getInt("worlds."+mainworld+".spawn.z", z);
		pi = MultiWorldMain.config.getInt("worlds."+mainworld+".spawn.pi", (int) pi);
		ya = MultiWorldMain.config.getInt("worlds."+mainworld+".spawn.ya", (int) ya);
		
		World main         = MultiWorldMain.instance.getServer().getWorld((String)MultiWorldMain.config.getProperty("main"));
		Location toRespawn = new Location(main, x, y, z, pi, ya);
		
		e.setRespawnLocation(toRespawn);
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
		
		whereIsPlayer.remove(e.getPlayer().getName(), world);
		worldsAndPlayers.remove(world.getName(), e.getPlayer().getName());
	}
}
