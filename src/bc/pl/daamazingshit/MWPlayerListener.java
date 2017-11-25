package bc.pl.daamazingshit;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MWPlayerListener extends PlayerListener {
	
	@Override
	public void onPlayerQuit(PlayerEvent e) {
		// jezeli gracz jest przypisany do hashmap (zawsze)
		if (MultiWorld.player.get(e.getPlayer().getWorld().getName()) != null) {
			MultiWorld.player.remove(e.getPlayer().getWorld().getName(), e.getPlayer());
			MultiWorld.player2.remove(e.getPlayer(), e.getPlayer().getWorld().getName());
		}
	}
	
	@Override
	public void onPlayerJoin(PlayerEvent e) {
		// jezeli gracz nie jest przypisany do hashmap (zawsze)
		if (MultiWorld.player.get(e.getPlayer().getWorld().getName()) == null) {
			MultiWorld.player.put(e.getPlayer().getWorld().getName(), e.getPlayer());
			MultiWorld.player2.put(e.getPlayer(), e.getPlayer().getWorld().getName());
		}
	}
	
	@Override
	public void onPlayerTeleport(PlayerMoveEvent e) {
		// jezeli swiat do ktorego sie teleportuje nie jest swiatem ktory jest w hashmap
		if (MultiWorld.player2.get(e.getPlayer()) != e.getTo().getWorld().getName()) {
			MultiWorld.player.remove(e.getPlayer().getWorld().getName(), e.getPlayer());
			MultiWorld.player2.remove(e.getPlayer(), e.getPlayer().getWorld().getName());
			MultiWorld.player.put(e.getPlayer().getWorld().getName(), e.getPlayer());
			MultiWorld.player2.put(e.getPlayer(), e.getPlayer().getWorld().getName());
		}
	}
}
