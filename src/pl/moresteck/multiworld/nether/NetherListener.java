package pl.moresteck.multiworld.nether;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.event.player.PlayerListener;

import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.portal.Portal;

public class NetherListener extends PlayerListener {
	//public static List<String> justTeleported = new LinkedList<String>();
	public static String prefix = ChatColor.RED + "[" + ChatColor.WHITE + "MultiWorld Nether" + ChatColor.RED + "] " + ChatColor.WHITE;
	public static List<String> k = new LinkedList<String>();

	// DANGEROUS TO USE

	/*@Override
	public void onPlayerMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if (e.getTo().getBlock() == e.getFrom().getBlock()) {
			return;
		}
		if (e.getFrom().getBlock().getType() == Material.PORTAL && e.getTo().getBlock().getType() != Material.PORTAL) {
			Timer task = new Timer();
			task.schedule(new TimerTask() {
				@Override
				public void run() {
					if (justTeleported.contains(p.getName())) {
						justTeleported.remove(p.getName());
					}
				}
			}, 2L);
		} else if (e.getFrom().getBlock().getType() != Material.PORTAL && e.getTo().getBlock().getType() == Material.PORTAL) {
			if (justTeleported.contains(p.getName())) {
				return;
			}
			final World pworld = p.getWorld();

			String toTpFirst = pworld.getEnvironment() == Environment.NETHER ? pworld.getName().split("_")[0] : pworld.getName() + "_nether";

			String link = Link.getLink(pworld.getName());
			String totp = link == null ? toTpFirst : link;
			World toTp = MultiWorld.server.getWorld(totp);

			if (toTp == null) {
				p.sendMessage(prefix + "There is no world named '" + totp + "' loaded.");
				return;
			}

			double blockRatio = p.getWorld().getEnvironment() == Environment.NETHER ? 8 : 0.125;

			Block player = p.getLocation().getBlock();
	        final Location toLocation = new Location(toTp, (player.getX() * blockRatio), player.getY(), (player.getZ() * blockRatio), p.getLocation().getYaw(), p.getLocation().getPitch());

			Timer task = new Timer();
			task.schedule(new TimerTask() {
				@Override
				public void run() {
					if (p.getLocation().getBlock().getType() != Material.PORTAL) {
						return;
					}
					if (MultiWorld.bukkitversion.getVersionId() >= 2) {
						p.teleport(toLocation);
					} else {	
						p.teleportTo(toLocation);
					}
					new PortalTravelAgent().findOrCreate(toLocation);
					justTeleported.add(p.getName());
					this.cancel();
				}
			}, 75L);
		}
	}*/

	public void onPlayerPortal(org.bukkit.event.player.PlayerPortalEvent e) {
		Location now = e.getFrom();
		String world = now.getWorld().getName();
		String totp = null;

		// Handles portals with fancy cooldown enabled
		Portal portal = Portal.getPortal(e.getPlayer().getLocation());
		if (portal != null) {
			e.useTravelAgent(false);
			e.setTo(portal.getDestination());
			return;
		}

		if (now.getWorld().getEnvironment() == Environment.SKYLANDS) {
			e.setCancelled(true);
			return;
		}
		String link = Link.getLink(world);
		totp = link == null ? totp : link;
		World toTp = null;
		try {
			toTp = MultiWorld.server.getWorld(totp);
		} catch (Exception ex) {
			e.getPlayer().sendMessage(prefix + "There is no world named '" + totp + "' loaded.");
			e.setCancelled(true);
			return;
		}

		double toScaling = (toTp.getEnvironment() == Environment.NETHER ? 8.0 : 1.0);
		double fromScaling = (now.getWorld().getEnvironment() == Environment.NETHER ? 8.0 : 1.0);
		now = this.getScaledLocation(now, fromScaling, toScaling);
		now.setWorld(toTp);
		e.setTo(now);
	}

	private Location getScaledLocation(final Location fromLocation, final double fromScaling, final double toScaling) {
        final double scaling = toScaling / fromScaling;
        fromLocation.setX(fromLocation.getX() * scaling);
        fromLocation.setZ(fromLocation.getZ() * scaling);
        return fromLocation;
    }
}
