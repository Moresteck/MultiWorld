package MWPortals.pl.amazingshit.mwp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

import com.nijikokun.bukkit.Permissions.Permissions;

import MWPortals.pl.amazingshit.mwp.mwp;
import MWPortals.pl.amazingshit.mwp.managers.PortalManager;

public class BlockListener12 extends BlockListener {

	@Override public void onBlockRightClick(BlockRightClickEvent e) {
		if (mwp.enabled == false) {
			return;
		}
		if (e.getPlayer().getInventory().getItemInHand().getType() == PortalManager.material()) {
			if (!Permissions.Security.has(e.getPlayer(), "mwp.create")) {
				return;
			}
			if (PortalListener.pos1.get(e.getPlayer()) != null) {
				if (PortalListener.pos1.get(e.getPlayer()).getWorld().getName() != e.getBlock().getLocation().getWorld().getName()) {
					PortalListener.pos1.remove(e.getPlayer());
					PortalListener.pos2.remove(e.getPlayer());
					e.getPlayer().sendMessage("Don't.");
					return;
				}
			}
			PortalListener.pos2.put(e.getPlayer(), e.getBlock().getLocation());
			e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Selected position 2.");
			return;
		}
	}

	@Override public void onBlockInteract(BlockInteractEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player)e.getEntity();
		if (p.getInventory().getItemInHand().getType() == PortalManager.material()) {
			if (!Permissions.Security.has(p, "mwp.create")) {
				return;
			}
			if (PortalListener.pos2.get(p) != null) {
				if (PortalListener.pos2.get(p).getWorld().getName() != e.getBlock().getLocation().getWorld().getName()) {
					PortalListener.pos1.remove(p);
					PortalListener.pos2.remove(p);
					p.sendMessage("Don't.");
					return;
				}
			}
			PortalListener.pos1.put(p, e.getBlock().getLocation());
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Selected position 1.");
			return;
		}
	}
}