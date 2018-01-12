package MWPortals.pl.amazingshit.mwp.listeners;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.nijikokun.bukkit.Permissions.Permissions;

import MWPortals.pl.amazingshit.mwp.managers.PortalManager;

public class PortalListener extends PlayerListener {

	public static HashMap<Player, Location> pos1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> pos2 = new HashMap<Player, Location>();

	@Override public void onPlayerMove(PlayerMoveEvent e) {
		Location tomove = e.getTo();
		if (PortalManager.enteredPortal(tomove)) {
			e.getPlayer().teleport(PortalManager.getPortalDefaultLoc(PortalManager.getPortalNameAtLocation(tomove)));
		}
	}

	@Override public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getPlayer().getInventory().getItemInHand().getType() == PortalManager.material()) {
				if (!Permissions.Security.has(e.getPlayer(), "mwp.create")) {
					return;
				}
				if (pos2.get(e.getPlayer()) != null) {
					if (pos2.get(e.getPlayer()).getWorld().getName() != e.getClickedBlock().getLocation().getWorld().getName()) {
						pos1.remove(e.getPlayer());
						pos2.remove(e.getPlayer());
						e.getPlayer().sendMessage("Don't.");
						return;
					}
				}
				pos1.put(e.getPlayer(), e.getClickedBlock().getLocation());
				e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Selected position 1.");
				return;
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().getInventory().getItemInHand().getType() == PortalManager.material()) {
				if (!Permissions.Security.has(e.getPlayer(), "mwp.create")) {
					return;
				}
				if (pos1.get(e.getPlayer()) != null) {
					if (pos1.get(e.getPlayer()).getWorld().getName() != e.getClickedBlock().getLocation().getWorld().getName()) {
						pos1.remove(e.getPlayer());
						pos2.remove(e.getPlayer());
						e.getPlayer().sendMessage("Don't.");
						return;
					}
				}
				pos2.put(e.getPlayer(), e.getClickedBlock().getLocation());
				e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Selected position 2.");
				return;
			}
		}
	}
}