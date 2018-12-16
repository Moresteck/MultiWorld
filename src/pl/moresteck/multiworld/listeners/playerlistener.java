package pl.moresteck.multiworld.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;
import pl.moresteck.multiworld.portal.Portal;

public class playerlistener extends PlayerListener {
	public static Map<String, Location> points1 = new HashMap<String, Location>();
	public static Map<String, Location> points2 = new HashMap<String, Location>();

	@Override
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.isCancelled()) return;
		Player p = e.getPlayer();
		Portal portal = Portal.getPortal(e.getTo());
		if (portal == null) return;

		if (portal.getFancyCooldown()) return; // Handled in NetherListener
		portal.portal(p);
	}

	public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent e) {
		if (!Perm.has(e.getPlayer(), "multiworld.portal.select")) return;
		if (e.getPlayer().getInventory().getItemInHand().getType() != Material.WOOD_AXE) return;
		if (e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) {
			if (!MultiWorld.worldEditEnabled && !Portal.isEqual(points1.get(e.getPlayer().getName()), e.getClickedBlock().getLocation()))
				e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "First position set.");

			points1.remove(e.getPlayer().getName());
			points1.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
			return;
		} else if (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
			if (!MultiWorld.worldEditEnabled && !Portal.isEqual(points2.get(e.getPlayer().getName()), e.getClickedBlock().getLocation()))
				e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Second position set.");

			points2.remove(e.getPlayer().getName());
			points2.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
			return;
		}
	}
}
