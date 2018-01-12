package MWPortals.pl.amazingshit.mwp.managers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import MWPortals.pl.amazingshit.mwp.mwp;
import pl.amazingshit.mw.util.ConfigUtil;

public class PortalManager {

	public static ConfigUtil config = new ConfigUtil(new File("plugins/MultiWorld/MWPortals", "portals.yml"));
	public static List<Location> matching = new LinkedList<Location>();

	public static void createPortal(String name, Location one, Location two, String destination) {
		config.load();
		config.setProperty("portals." + name + ".x1", one.getBlockX());
		config.setProperty("portals." + name + ".y1", one.getBlockY());
		config.setProperty("portals." + name + ".z1", one.getBlockZ());
		
		config.setProperty("portals." + name + ".x2", two.getBlockX());
		config.setProperty("portals." + name + ".y2", two.getBlockY());
		config.setProperty("portals." + name + ".z2", two.getBlockZ());
		
		config.setProperty("portals." + name + ".world", one.getWorld().getName());
		config.setProperty("portals." + name + ".destination.world", destination);
		config.save_();
	}

	public static void createLPortal(String name, Location one, Location two, String destination) {
		config.load();
		config.setProperty("portals." + name + ".x1", one.getBlockX());
		config.setProperty("portals." + name + ".y1", one.getBlockY());
		config.setProperty("portals." + name + ".z1", one.getBlockZ());
		
		config.setProperty("portals." + name + ".x2", two.getBlockX());
		config.setProperty("portals." + name + ".y2", two.getBlockY());
		config.setProperty("portals." + name + ".z2", two.getBlockZ());
		
		config.setProperty("portals." + name + ".world", one.getWorld().getName());
		config.setProperty("portals." + name + ".destination.portal", destination);
		config.save_();
	}

	public static List<String> allPortals() {
		config.load();
		if (config.getProperty("portals") == null) {
			return null;
		}
		List<String> portals = config.getKeys("portals");
		return portals;
	}

	public static Location getPortalDefaultLoc(String name) {
		config.load();
		if (exists(name)) {
			int x = config.getInt("portals." + name + ".x1", 0);
			int z = config.getInt("portals." + name + ".z1", 0);
			String world = config.getString("portals." + name + ".world", null);
			World w = mwp.instance.getServer().getWorld(world);
			
			Location case1 = new Location(w, x-1, w.getHighestBlockYAt(x-1, z-1), z-1);
			Location case2 = new Location(w, x+1, w.getHighestBlockYAt(x+1, z+1), z+1);
			if (enteredPortal(case1)) {
				return case2;
			}
			if (enteredPortal(case1)) {
				return case1;
			}
		}
		return null;
	}

	public static boolean enteredPortal(Location loc) {
		config.load();
		if (config.getProperty("portals") == null) {
			return false;
		}
		List<String> portals = config.getKeys();
		boolean entered = true;
		for (String portal: portals) {
			int x1 = config.getInt("portals." + portal + ".x1", 0);
			int y1 = config.getInt("portals." + portal + ".y1", 0);
			int z1 = config.getInt("portals." + portal + ".z1", 0);
			
			int x2 = config.getInt("portals." + portal + ".x2", 0);
			int y2 = config.getInt("portals." + portal + ".y2", 0);
			int z2 = config.getInt("portals." + portal + ".z2", 0);
			
			if (!loc.getWorld().getName().equalsIgnoreCase(config.getString("portals." + portal + ".world", null))) {
				return false;
			}
			
			if ((loc.getBlockX() < x1) || (loc.getBlockX() > x2)) {
				entered = false;
			}
			if ((loc.getBlockZ() < z1) || (loc.getBlockZ() > z2)) {
				entered = false;
			}
			if ((loc.getBlockY() < y1) || (loc.getBlockY() > y2)) {
				entered = false;
			}
		}
		return entered;
	}

	public static String getPortalNameAtLocation(Location loc) {
		config.load();
		if (config.getProperty("portals") == null) {
			return null;
		}
		String name = null;
		List<String> portals = config.getKeys();
		for (String portal: portals) {
			int x1 = config.getInt("portals." + portal + ".x1", 0);
			int y1 = config.getInt("portals." + portal + ".y1", 0);
			int z1 = config.getInt("portals." + portal + ".z1", 0);
			
			int x2 = config.getInt("portals." + portal + ".x2", 0);
			int y2 = config.getInt("portals." + portal + ".y2", 0);
			int z2 = config.getInt("portals." + portal + ".z2", 0);
			
			if (!loc.getWorld().getName().equalsIgnoreCase(config.getString("portals." + portal + ".world", null))) {
				return null;
			}
			
			if ((loc.getBlockX() < x1) || (loc.getBlockX() > x2)) {
				name = portal;
			}
			if ((loc.getBlockZ() < z1) || (loc.getBlockZ() > z2)) {
				name = portal;
			}
			if ((loc.getBlockY() < y1) || (loc.getBlockY() > y2)) {
				name = portal;
			}
		}
		return name;
	}

	public static void removePortal(String name) {
		config.load();
		if (exists(name)) {
			config.removeProperty("portals." + name);
			config.save_();
		}
		return;
	}

	public static void defaultConfig() {
		config.load();
		config.setProperty("tool", "STICK");
		config.setProperty("portals", null);
		config.save_();
	}

	public static Boolean exists(String portal) {
		config.load();
		return config.getProperty("portals." + portal) != null;
	}

	public static Boolean exists() {
		config.load();
		if (config.getProperty("tool") != null) {
			return true;
		}
		return false;
	}

	public static void printDestination(String portal, CommandSender sender) {
		config.load();
		int x1 = config.getInt("portals." + portal + ".x1", 0);
		int y1 = config.getInt("portals." + portal + ".y1", 0);
		int z1 = config.getInt("portals." + portal + ".z1", 0);
		
		int x2 = config.getInt("portals." + portal + ".x2", 0);
		int y2 = config.getInt("portals." + portal + ".y2", 0);
		int z2 = config.getInt("portals." + portal + ".z2", 0);
		String destination = config.getString("portals." + portal + ".destination.world", null) != null ? config.getString("portals." + portal + ".destination.world", null) : config.getString("portals." + portal + ".destination.portal", null);
		sender.sendMessage("Portal " + portal + " info");
		sender.sendMessage("Destination: " + destination);
		sender.sendMessage("First position: x" + x1 + ", y" + y1 + ", z" + z1);
		sender.sendMessage("Second position: x" + x2 + ", y" + y2 + ", z" + z2);
		return;
	}

	public static Material material() {
		config.load();
		Material ret = Material.getMaterial(config.getString("tool", "STICK"));
		return ret;
	}
}