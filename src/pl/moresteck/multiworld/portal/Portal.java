package pl.moresteck.multiworld.portal;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import bukkit.util.config.Configuration;
import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;

public class Portal {
	private static Configuration config = new Configuration(new File("plugins/MultiWorld", "portals.yml"));
	private String name;

	public Portal(String name) {
		this.name = name;
	}

	public static Portal getPortal(Location loc) {
		if (loc == null) return null;
		Portal portal = null;
		config.load();
		if (config.getKeys() == null) {
			return null;
		}
		for (String portals : config.getKeys()) {
			Portal prtl = new Portal(portals);
			if (isIn(prtl.getLocations(), loc)) {
				portal = prtl;
			}
		}
		return portal;
	}

	public static List<Portal> getPortals() {
		config.load();
		List<Portal> portals = new LinkedList<Portal>();
		for (String portal : config.getKeys()) {
			portals.add(new Portal(portal));
		}
		return portals;
	}

	public static boolean portalExists(String name) {
		if (name == null) return false;
		config.load();
		String loc1 = config.getString(name + ".location1", null);
		String loc2 = config.getString(name + ".location2", null);
		String to = config.getString(name + ".destination", null);
		if (loc1 == null || loc2 == null || to == null) return false;
		return true;
	}

	public static Portal createPortal(String name, Location loc1, Location loc2, String dest, boolean cooldown) {
		if (name == null || dest == null || loc2 == null || loc1 == null) return null;
		Portal p = new Portal(name);
		config.load();
		config.setProperty(name + ".destination", dest);
		config.save();
		p.setLocations(loc1, loc2);
		p.setFancyCooldown(cooldown);
		return p;
	}

	public void setFancyCooldown(boolean value) {
		config.load();
		config.setProperty(this.name + ".fancy-cooldown", value);
		config.save();
	}

	public boolean getFancyCooldown() {
		config.load();
		boolean value = config.getBoolean(this.name + ".fancy-cooldown", false);
		// No PlayerPortalEvent before 1.6.6
		if (value && MultiWorld.bukkitversion.getVersionId() <= 9) {
			MultiWorld.log.info("Please note that 'fancy-cooldown' works only if the version is higher than b1.6.5");
			return false;
		}
		return value;
	}

	public boolean removePortal() {
		try {
			config.load();
			config.removeProperty(this.name);
			config.save();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public String getName() {
		return this.name;
	}

	public boolean portal(Player p) {
		if (p == null) return false;
		if (MultiWorld.bukkitversion.getVersionId() >= 2) {
			p.teleport(this.getDestination());
		} else {
			p.teleportTo(this.getDestination());
		}
		return true;
	}

	public Location getDestination() {
		if (!portalExists(this.name)) {
			MultiWorld.log.info("The destination for portal '" + this.name + "' is not valid!");
			return null;
		}
		config.load();
		String destination = config.getString(this.name + ".destination", null);
		Location dest = null;
		if (destination.startsWith("W:")) {
			String[] split = destination.split(":");
			MWorld mworld = MultiWorld.getWorld(split[1]);
			if (mworld == null) {
				MultiWorld.log.info("The destination world for portal '" + this.name + "' is not valid!");
				return null;
			}
			dest = mworld.getWorld().getSpawnLocation();
		} else {
			String[] split = destination.split(",");
			MWorld mworld = MultiWorld.getWorld(split[0]);
			if (mworld == null) {
				MultiWorld.log.info("The destination world for portal '" + this.name + "' is not valid!");
				return null;
			}
			int x, y, z;
			try {
				x = Integer.parseInt(split[1]);
				y = Integer.parseInt(split[2]);
				z = Integer.parseInt(split[3]);
			} catch (Exception ex) {
				MultiWorld.log.info("The destination coordinates for portal '" + this.name + "' are not valid!");
				return null;
			}
			dest = new Location(mworld.getWorld(), x, y, z);
		}
		return dest;
	}

	public Location[] getLocations() {
		if (!portalExists(this.name)) {
			MultiWorld.log.info("The locations of portal '" + this.name + "' are not valid!");
			return null;
		}
		config.load();
		String location1 = config.getString(this.name + ".location1", null);
		String location2 = config.getString(this.name + ".location2", null);
		Location loc1 = null;
		Location loc2 = null;
		String[] split1 = location1.split(",");
		String[] split2 = location2.split(",");
		MWorld mworld = MultiWorld.getWorld(split1[0]);
		if (mworld == null) {
			MultiWorld.log.info("The world locations of portal '" + this.name + "' are not valid!");
			return null;
		}
		int x1, y1, z1, x2, y2, z2;
		try {
			x1 = Integer.parseInt(split1[1]);
			y1 = Integer.parseInt(split1[2]);
			z1 = Integer.parseInt(split1[3]);

			x2 = Integer.parseInt(split2[1]);
			y2 = Integer.parseInt(split2[2]);
			z2 = Integer.parseInt(split2[3]);
		} catch (Exception ex) {
			MultiWorld.log.info("The location coordinates of portal '" + this.name + "' are not valid!");
			return null;
		}
		loc1 = new Location(mworld.getWorld(), x1, y1, z1);
		loc2 = new Location(mworld.getWorld(), x2, y2, z2);
		Location[] loc = new Location[2];
		loc[0] = loc1;
		loc[1] = loc2;
		return loc;
	}

	public boolean setLocations(Location loc1, Location loc2) {
		if (loc1 == null || loc2 == null) return false;
		config.load();
		String world = loc1.getWorld().getName();
		int x1, y1, z1, x2, y2, z2;
		x1 = loc1.getBlockX();
		y1 = loc1.getBlockY();
		z1 = loc1.getBlockZ();

		x2 = loc2.getBlockX();
		y2 = loc2.getBlockY();
		z2 = loc2.getBlockZ();
		if (x1 > x2) {
			x1 = loc2.getBlockX();
			x2 = loc1.getBlockX();
		}
		if (y1 > y2) {
			y1 = loc2.getBlockY();
			y2 = loc1.getBlockY();
		}
		if (z1 > z2) {
			z1 = loc2.getBlockZ();
			z2 = loc1.getBlockZ();
		}
		String location1 = world + "," + x1 + "," + y1 + "," + z1;
		String location2 = world + "," + x2 + "," + y2 + "," + z2;
		config.setProperty(this.name + ".location1", location1);
		config.setProperty(this.name + ".location2", location2);
		config.save();
		return true;
	}

	public boolean setDestination(Location loc) {
		if (loc == null) return false;
		config.load();
		World world = loc.getWorld();
		String worldname = world.getName();
		if (isEqual(world.getSpawnLocation(), loc)) {
			config.setProperty(this.name + ".destination", "W:" + worldname);
			return true;
		}
		int x, y, z;
		x = loc.getBlock().getX();
		y = loc.getBlock().getY();
		z = loc.getBlock().getZ();
		String location = worldname + "," + x + "," + y + "," + z;
		config.setProperty(this.name + ".destination", location);
		config.save();
		return true;
	}

	public Destination getDestinationType() {
		if (!portalExists(this.name)) {
			MultiWorld.log.info("The destination for portal '" + this.name + "' is not valid!");
			return null;
		}
		config.load();
		String destination = config.getString(this.name + ".destination", null);
		if (destination.startsWith("W:")) return Destination.WORLD;
		else return Destination.LOCATION;
	}

	public enum Destination {
		WORLD,
		LOCATION;
	}

	public static boolean isIn(Location[] in, Location loc) {
		if (in == null || loc == null) return false;
		if (!loc.getWorld().getName().equals(in[0].getWorld().getName())) return false;
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return ((x >= in[0].getBlockX() && x <= in[1].getBlockX()) &&            
                (z >= in[0].getBlockZ() && z <= in[1].getBlockZ()) &&           
                (y >= in[0].getBlockY() && y <= in[1].getBlockY()));
    }

	public static boolean isEqual(Location l1, Location l2) {
		if (l1 == null || l2 == null) return false;
		Block b1 = l1.getBlock();
		Block b2 = l2.getBlock();
		if (b1.getX() == b2.getX() && b1.getY() == b2.getY() && b1.getZ() == b2.getZ()) {
			return true;
		}
		return false;
	}

	public static String getFormattedLocation(Location loc) {
		return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
	}
}
