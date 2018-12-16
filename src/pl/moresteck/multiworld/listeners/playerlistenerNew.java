package pl.moresteck.multiworld.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.portal.Portal;

public class playerlistenerNew implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.isCancelled()) return;
		Player p = e.getPlayer();
		Portal portal = Portal.getPortal(e.getTo());
		if (portal == null) return;

		if (portal.getFancyCooldown()) return; // Handled in NetherListener
		portal.portal(p);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
		if (e.getPlayer().getInventory().getItemInHand().getType() != Material.WOOD_AXE) return;
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (!MultiWorld.worldEditEnabled) e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "First position set.");
			playerlistener.points1.remove(e.getPlayer().getName());
			playerlistener.points1.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
			return;
		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (!MultiWorld.worldEditEnabled) e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Second position set.");
			playerlistener.points2.remove(e.getPlayer().getName());
			playerlistener.points2.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
			return;
		}
	}
}
