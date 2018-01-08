package pl.daamazingshit.mw.managers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.util.config.Configuration;

import pl.daamazingshit.mw.util.Explode;
import pl.daamazingshit.mw.util.PropertyType;

public class ConfigWorld {

	private static Configuration db = new Configuration(new File("plugins/MultiWorld", "worlds.yml"));

	public static List<WorldManager> getWorldList() {
		db.load();
		List<String> worlds = new LinkedList<String>();
		worlds = db.getKeys("worlds");
		List<WorldManager> ret = new LinkedList<WorldManager>();
		for (String all: worlds) {
			WorldManager wm = new WorldManager(all, getEnvironment(all), getSeed(all));
			ret.add(wm);
		}
		return ret.isEmpty() ? null : ret;
	}

	public static Environment getEnvironment(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			String env = null; env = db.getString("worlds."+world+".environment", env); env.toUpperCase();
			Environment ret = Environment.valueOf(env);
			return ret;
		}
		return null;
	}

	public static Long getSeed(String world) {
		db.load();
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
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".monsters", allowed);
			return allowed;
		}
		return false;
	}

	private static Boolean getAllowSpawnAnimals(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".animals", allowed);
			return allowed;
		}
		return false;
	}

	private static Boolean getAllowPVP(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".pvp", allowed);
			return allowed;
		}
		return false;
	}

	public static Boolean setAllow(PropertyType type, String world, boolean set) {
		if (!exists(world)) {
			return false;
		}
		Boolean result = false;
		switch (type) {
		case MONSTERS:
			result = setAllowSpawnMonsters(world, set);
		case ANIMALS:
			result = setAllowSpawnAnimals(world, set);
		case PVP:
			result = setAllowPVP(world, set);
		}
		return result;
	}

	private static Boolean setAllowSpawnMonsters(String world, boolean set) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			db.setProperty("worlds."+world+".monsters", set);
			db.save();
			return true;
		}
		return false;
	}

	private static Boolean setAllowSpawnAnimals(String world, boolean set) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			db.setProperty("worlds."+world+".animals", set);
			db.save();
			return true;
		}
		return false;
	}

	private static Boolean setAllowPVP(String world, boolean set) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			db.setProperty("worlds."+world+".pvp", set);
			db.save();
			return true;
		}
		return false;
	}

	public static Boolean allowExplode(Explode ex, String world) {
		if (getExplodeAll(world) == "true") {
			return true;
		} else if (getExplodeAll(world) == "false") {
			return false;
		}
		else if (getExplodeAll(world) == "ignore") {
			switch (ex) {
			case TNT:
				if (getExplodeTNT(world)) {
					return true;
				}
				return false;
			case OTHER:
				if (getExplodeOther(world)) {
					return true;
				}
				return false;
			case CREEPER:
				if (getExplodeCreeper(world)) {
					return true;
				}
				return false;
			case CUSTOM:
				if (getExplodePlugin(world)) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

	private static Boolean getExplodeCreeper(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".explosions.creeper", allowed);
			return allowed;
		}
		return false;
	}

	private static Boolean getExplodeTNT(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".explosions.tnt", allowed);
			return allowed;
		}
		return false;
	}

	private static Boolean getExplodeOther(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".explosions.other", allowed);
			return allowed;
		}
		return false;
	}

	private static String getExplodeAll(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			String allowed = "ignore"; allowed = db.getString("worlds."+world+".explosions.all", allowed);
			return allowed;
		}
		return "ignore";
	}

	private static Boolean getExplodePlugin(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			Boolean allowed = true; allowed = db.getBoolean("worlds."+world+".explosions.custom", allowed);
			return allowed;
		}
		return true;
	}

	public static Boolean remove(String world) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			try {
				db.removeProperty("worlds."+world);
				db.save();
				return true;
			}
			catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	public static Boolean add(String world, Environment environment, boolean allowPvp, boolean allowMonsters, boolean allowAnimals, long seed) {
		db.load();
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
			db.setProperty("worlds."+world+".explosions.all", true);
			db.setProperty("worlds."+world+".explosions.other", true);
			db.setProperty("worlds."+world+".explosions.tnt", true);
			db.setProperty("worlds."+world+".explosions.creeper", true);
			db.setProperty("worlds."+world+".explosions.custom", true);
			db.save();
			return true;
		}
		return false;
	}

	public static Boolean exists(String world) {
		db.load();
		return db.getProperty("worlds."+world) != null;
	}
}
