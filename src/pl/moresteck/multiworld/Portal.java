package pl.moresteck.multiworld;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;

import bukkit.util.config.Configuration;
import pl.moresteck.multiworld.world.MWorld;

public class Portal {
	private static Configuration config = new Configuration(new File("plugins/MultiWorld", "portals.yml"));
	private String name;

	public Portal(String name) {
		this.name = name;
	}

	public static boolean portalExists(String name) {
		config.load();
		String loc = config.getString(name + ".location", null);
		if (loc == null) return false;
		String to = config.getString(name + ".destination", null);
		if (to == null) return false;
		if (!to.startsWith("W:") && !to.startsWith("L:")) return false;
		return true;
	}

	public static boolean createPortal(String name, String dest) {
		config.load();
		if (!dest.startsWith("W:") && !dest.startsWith("L:")) return false;
		config.setProperty(name + ".destination", dest);
		config.save();
		return true;
	}

	public Location getDestination() {
		if (!portalExists(name)) {
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
		} else if (destination.startsWith("L:")) {
			String[] split_loc = destination.split(":");
			String[] split = split_loc[1].split(",");
			MWorld mworld = MultiWorld.getWorld(split[0]);
			if (mworld == null) {
				MultiWorld.log.info("The destination world for portal '" + this.name + "' is not valid!");
				return null;
			}
			int x, y, z;
			float p, yaw;
			try {
				x = Integer.parseInt(split[1]);
				y = Integer.parseInt(split[2]);
				z = Integer.parseInt(split[3]);
				p = Float.parseFloat(split[4]);
				yaw = Float.parseFloat(split[5]);
			} catch (Exception ex) {
				MultiWorld.log.info("The destination coordinates for portal '" + this.name + "' are not valid!");
				return null;
			}
			dest = new Location(mworld.getWorld(), x, y, z, p, yaw);
		} else {
			MultiWorld.log.info("Unsupported portal destination for portal '" + this.name + "'!");
			return null;
		}
		return dest;
	}

	public boolean setLocation(Location loc) {
		if (loc == null) return false;
		config.load();
		String world = loc.getWorld().getName();
		double x, y, z;
		float p, yaw;
		x = loc.getBlockX();
		y = loc.getBlockY();
		z = loc.getBlockZ();
		p = loc.getPitch();
		yaw = loc.getYaw();
		String location = world + "," + x + "," + y + "," + z + "," + p + "," + yaw;
		config.setProperty(this.name + ".location", location);
		config.save();
		return true;
	}

	public boolean setDestination(Location loc) {
		if (loc == null) return false;
		config.load();
		World world = loc.getWorld();
		String worldname = world.getName();
		if (world.getSpawnLocation().getBlock() == loc.getBlock()) {
			config.setProperty(this.name + ".location", "W:" + worldname);
			return true;
		}
		double x, y, z;
		float p, yaw;
		x = loc.getBlockX();
		y = loc.getBlockY();
		z = loc.getBlockZ();
		p = loc.getPitch();
		yaw = loc.getYaw();
		String location = worldname + "," + x + "," + y + "," + z + "," + p + "," + yaw;
		config.setProperty(this.name + ".location", location);
		config.save();
		return true;
	}
}
