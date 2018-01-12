package pl.amazingshit.mw.managers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.World.Environment;
import org.bukkit.util.config.Configuration;

import pl.amazingshit.mw.util.Explode;
import pl.amazingshit.mw.util.PropertyType;

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
		return ret;
	}

	public static List<String> getWorldStringList() {
		db.load();
		List<String> worlds = new LinkedList<String>();
		worlds = db.getKeys("worlds");
		return worlds;
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

	public static Configuration getDatabase() {
		return db;
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
		if (type == PropertyType.MONSTERS)
			result = getAllowSpawnMonsters(world);
		
		if (type == PropertyType.ANIMALS)
			result = getAllowSpawnAnimals(world);
		
		if (type == PropertyType.PVP)
			result = getAllowPVP(world);
		
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
		if (type == PropertyType.MONSTERS)
			result = setAllowSpawnMonsters(world, set);
		
		if (type == PropertyType.ANIMALS)
			result = setAllowSpawnAnimals(world, set);
		
		if (type == PropertyType.PVP)
			result = setAllowPVP(world, set);
		
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
			if (ex == Explode.TNT) {
				if (getExplodeTNT(world)) {
					return true;
				}
				return false;
			}
			if (ex == Explode.CREEPER) {
				if (getExplodeOther(world)) {
					return true;
				}
				return false;
				}
			if (ex == Explode.CREEPER) {
				if (getExplodeCreeper(world)) {
					return true;
				}
				return false;
			}
			if (ex == Explode.CUSTOM) {
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

	public static Boolean add(String world, Environment environment, boolean allowPvp, boolean allowMonsters, boolean allowAnimals, boolean creeperEx, String allEx, boolean tntEx, boolean otherEx, boolean customEx, long seed) {
		db.load();
		if (environment == Environment.NETHER) {
			db.setProperty("worlds."+world+".environment", "NETHER");
		}
		if (environment == Environment.NORMAL) {
			db.setProperty("worlds."+world+".environment", "NORMAL");
		}
		db.setProperty("worlds."+world+".pvp", allowPvp);
		db.setProperty("worlds."+world+".monsters", allowMonsters);
		db.setProperty("worlds."+world+".animals", allowAnimals);
		db.setProperty("worlds."+world+".seed", seed);
		db.setProperty("worlds."+world+".explosions.all", allEx);
		db.setProperty("worlds."+world+".explosions.other", otherEx);
		db.setProperty("worlds."+world+".explosions.tnt", tntEx);
		db.setProperty("worlds."+world+".explosions.creeper", creeperEx);
		db.setProperty("worlds."+world+".explosions.custom", customEx);
		db.save();
		return true;
	}

	public static Boolean exists(String world) {
		db.load();
		return db.getProperty("worlds."+world) != null;
	}

	public static Boolean rename(String world, String renamed) {
		db.load();
		if (db.getProperty("worlds."+world) != null) {
			String name = renamed;
			Environment env = getEnvironment(world);
			boolean allowPvp = getAllow(PropertyType.PVP, world);
			boolean allowMonsters = getAllow(PropertyType.MONSTERS, world);
			boolean allowAnimals = getAllow(PropertyType.ANIMALS, world);
			long seed = getSeed(world);
			boolean creeper = allowExplode(Explode.CREEPER, world);
			boolean tnt = allowExplode(Explode.TNT, world);
			boolean other = allowExplode(Explode.OTHER, world);
			boolean custom = allowExplode(Explode.CUSTOM, world);
			String exAll = getExplodeAll(world);
			
			WorldManager old = new WorldManager(world, env, seed);
			add(name, env, allowPvp, allowMonsters, allowAnimals, creeper, exAll, tnt, other, custom, seed);
			old.unloadWorld(true);
			
			File rename = new File(world);
			rename.renameTo(new File(renamed));
			
			WorldManager wm = new WorldManager(name, env, seed);
			wm.createWorld();
			db.removeProperty("worlds."+world);
			db.save();
			return true;
		}
		return false;
	}
}