package pl.daamazingshit.mw.managers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.util.config.Configuration;

import pl.daamazingshit.mw.util.PropertyType;

public class ConfigWorld {

	private static Configuration db = new Configuration(new File("plugins/MultiWorld", "worlds.yml"));

	public static List<WorldManager> getWorldList() {
		List<String> worlds = new LinkedList<String>();
		worlds = db.getKeys("worlds");
		List<WorldManager> ret = new LinkedList<WorldManager>();
		for (String all : worlds) {
			WorldManager wm = new WorldManager(all, getEnvironment(all), getSeed(all));
			ret.add(wm);
		}
		return ret.isEmpty() ? null : ret;
	}

	public static Environment getEnvironment(String world) {
		if (db.getProperty("worlds."+world) != null) {
			String env = null; env = db.getString("worlds."+world+".environment", env); env.toUpperCase();
			Environment ret = Environment.valueOf(env);
			return ret;
		}
		return null;
	}

	public static Long getSeed(String world) {
		if (db.getProperty("worlds."+world) != null) {
			String seed = null; seed = db.getString("worlds."+world+".seed", seed);
			Long ret = Long.parseLong(seed);
			return ret;
		}
		return null;
	}

	public static Boolean getAllow(PropertyType type, String world) {
		Boolean result = false;
		switch (type) {
		case MONSTERS:
			result = getAllowSpawnMonsters(world);
		case ANIMALS:
			result = getAllowSpawnAnimals(world);
		case PVP:
			result = getAllowPVP(world);
		}
		return result;
	}

	private static Boolean getAllowSpawnMonsters(String world) {
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".monsters", allowed);
			return allowed;
		}
		return null;
	}

	private static Boolean getAllowSpawnAnimals(String world) {
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".animals", allowed);
			return allowed;
		}
		return null;
	}

	private static Boolean getAllowPVP(String world) {
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".pvp", allowed);
			return allowed;
		}
		return null;
	}

	public static Boolean remove(String world) {
		if (db.getProperty("worlds."+world) != null) {
			try {
				db.removeProperty("worlds."+world);
				return true;
			}
			catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	public static Boolean add(String world, Environment environment, boolean allowPvp, boolean allowMonsters, boolean allowAnimals, long seed) {
		if (db.getProperty("worlds."+world) == null) {
			switch (environment) {
			case NORMAL:
			    db.setProperty("worlds."+world+".environment", "NORMAL");
			case NETHER:
				db.setProperty("worlds."+world+".environment", "NETHER");
			}
			db.setProperty("worlds."+world+".pvp", allowPvp);
			db.setProperty("worlds."+world+".monsters", allowMonsters);
			db.setProperty("worlds."+world+".animals", allowAnimals);
			db.setProperty("worlds."+world+".seed", seed);
			return true;
		}
		return false;
	}
}
