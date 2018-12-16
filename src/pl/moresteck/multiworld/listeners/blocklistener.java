package pl.moresteck.multiworld.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.Perm;
import pl.moresteck.multiworld.portal.Portal;

public class blocklistener extends BlockListener {

	@Override
	public void onBlockDamage(BlockDamageEvent e) {
		if (e.isCancelled()) return;
		if (!Perm.has(e.getPlayer(), "multiworld.portal.select")) return;
		if (playerlistener.points1.containsKey(e.getPlayer().getName())) {
			if (Portal.isEqual(playerlistener.points1.get(e.getPlayer().getName()), e.getBlock().getLocation())) return;
			playerlistener.points1.remove(e.getPlayer().getName());
		}
		if (!MultiWorld.worldEditEnabled) e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "First position set.");
		playerlistener.points1.put(e.getPlayer().getName(), e.getBlock().getLocation());
	}

	@Override
	public void onBlockRightClick(BlockRightClickEvent e) {
		if (!Perm.has(e.getPlayer(), "multiworld.portal.select")) return;
		if (playerlistener.points2.containsKey(e.getPlayer().getName())) {
			if (Portal.isEqual(playerlistener.points2.get(e.getPlayer().getName()), e.getBlock().getLocation())) return;
			playerlistener.points2.remove(e.getPlayer().getName());
		}
		if (!MultiWorld.worldEditEnabled) e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Second position set.");
		playerlistener.points2.put(e.getPlayer().getName(), e.getBlock().getLocation());
	}
}
