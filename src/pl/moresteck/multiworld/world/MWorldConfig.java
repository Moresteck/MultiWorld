package pl.moresteck.multiworld.world;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Slime;
import org.bukkit.entity.WaterMob;
import org.bukkit.util.config.Configuration;

import pl.moresteck.bvnpe.BukkitVersion;
import pl.moresteck.multiworld.MultiWorld;

public class MWorldConfig {
	static Configuration worlds = new Configuration(new File("plugins/MultiWorld", "worlds.yml"));

	public static String[] getWorlds() {
		worlds.load();
		if (worlds.getKeys("worlds") == null) {
			createBasicConfig();
		}
		String[] array = new String[worlds.getKeys("worlds").size()];
		for (int i = 0; i < worlds.getKeys("worlds").size(); i++) {
			String name = worlds.getKeys("worlds").get(i);
			array[i] = name;
		}
		return array;
	}

	protected static void createBasicConfig() {
		worlds.load();
		worlds.setProperty("craftbukkits_download", "https://betacraft.ovh/bukkit");
		worlds.save();

		createBasicConfig("world", "NORMAL");
		if (BukkitVersion.getVersionId() >= 9) {
			createBasicConfig("world_nether", "NETHER");
		}
	}

	public static void createBasicConfig(String world, String env) {
		worlds.load();
		worlds.setProperty("worlds." + world + ".environment", env);
		worlds.setProperty("worlds." + world + ".seed", new MWorld(world).getWorld().getId());
		worlds.setProperty("worlds." + world + ".pvp", true);
		worlds.setProperty("worlds." + world + ".monsters.spawn", true);
		worlds.setProperty("worlds." + world + ".animals.spawn", true);
		worlds.setProperty("worlds." + world + ".monsters.exceptfor", "");
		worlds.setProperty("worlds." + world + ".animals.exceptfor", "");
		// b1.6.6+
		if (BukkitVersion.getVersionId() >= 9) {
			worlds.setProperty("worlds." + world + ".generator", "");
		}
		worlds.save();
	}

	public static String getGenerator(String name) {
		if (BukkitVersion.getVersionId() >= 9) {
			worlds.load();
			return worlds.getString("worlds." + name + ".generator", "");
		} else {
			return "";
		}
	}

	public static void setGenerator(String name, String generator) {
		if (BukkitVersion.getVersionId() >= 9) {
			worlds.load();
			worlds.setProperty("worlds." + name + ".generator", generator);
			worlds.save();
		} else {
			return;
		}
	}

	/**
	 * Gets the environment of specified world.
	 *
	 * @param name World's name
	 * @return World's environment
	 */
	public static Environment getEnvironment(String name) {
		worlds.load();
		String string = worlds.getString("worlds." + name + ".environment", "NORMAL");
		Environment env = Environment.valueOf(string);
		return env;
	}

	/**
	 * Gets the seed of specified world.
	 *
	 * @param name World's name
	 * @return The seed of the world
	 */
	public static long getSeed(String name) {
		World w = new MWorld(name).getWorld();
		worlds.load();
		String seed = worlds.getString("worlds." + name + ".seed", "gargamel");
		long numberized;
		try {
			numberized = Long.parseLong(seed);
			if (w != null) {
				if (numberized != w.getId()) {
					// Keep in sync with the configuration.
					setSeed(name, Long.toString(w.getId()));
					return w.getId();
				}
			}
		} catch (NumberFormatException ex) {
			if (seed.length() > 0) {
				numberized = seed.hashCode();
			} else {
				Long newseed;
				if (w == null) {
					newseed = new Random().nextLong();
				} else {
					newseed = w.getId();
				}
				numberized = newseed;
				// Keep in sync with the configuration;
				setSeed(name, newseed.toString());
			}
		}
		return numberized;
	}

	/**
	 * Tells if PvP is enabled in specified world.
	 *
	 * @param name World's name
	 * @return PvP allowed in the world
	 */
	public static boolean getPvP(String name) {
		worlds.load();
		boolean bool = worlds.getBoolean("worlds." + name + ".pvp", true);
		return bool;
	}

	/**
	 * Tells if monsters are allowed to spawn in specified world.
	 *
	 * @param name World's name
	 * @return Monsters allowed to spawn
	 */
	public static boolean getAllowMonsters(String name) {
		worlds.load();
		boolean diff = worlds.getBoolean("worlds." + name + ".monsters.spawn", true);
		return diff;
	}

	/**
	 * Tells if animals are allowed to spawn in specified world.
	 *
	 * @param name World's name
	 * @return Animals allowed to spawn
	 */
	public static boolean getAllowAnimals(String name) {
		worlds.load();
		boolean diff = worlds.getBoolean("worlds." + name + ".animals.spawn", true);
		return diff;
	}

	/**
	 * Tells if the entity is allowed to spawn in specified world.
	 *
	 * @param name World's name
	 * @param type Entity
	 * @return Entity allowed to spawn
	 */
	public static boolean getAllowSpawn(String name, Entity type) {
		worlds.load();
		if (type instanceof Monster || type instanceof Ghast ||
				type instanceof Slime) {
			boolean cbool = worlds.getBoolean("worlds." + name + ".monsters.spawn", true);
			List<String> except = worlds.getStringList("worlds." + name + ".monsters.exceptfor", null);
			if (except == null || except.isEmpty()) {
				return cbool;
			}
			if (cbool) {
				if (except.contains(MultiWorld.getName(type))) {
					return false;
				}
				return true;
			} else {
				if (except.contains(MultiWorld.getName(type))) {
					return true;
				}
				return false;
			}
		} else if (type instanceof Animals || type instanceof WaterMob) {
			boolean cbool = worlds.getBoolean("worlds." + name + ".animals.spawn", true);
			List<String> except = worlds.getStringList("worlds." + name + ".animals.exceptfor", null);
			if (except == null || except.isEmpty()) {
				return cbool;
			}
			if (cbool) {
				if (except.contains(MultiWorld.getName(type))) {
					return false;
				}
				return true;
			} else {
				if (except.contains(MultiWorld.getName(type))) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

	public static void setSeed(String name, String seed) {
		worlds.load();
		worlds.setProperty("worlds." + name + ".seed", seed);
		worlds.save();
	}

	public static void setPvP(String name, boolean pvp) {
		worlds.load();
		worlds.setProperty("worlds." + name + ".pvp", pvp);
		worlds.save();
	}

	public static void setAllowMonsters(String name, boolean set) {
		worlds.load();
		worlds.setProperty("worlds." + name + ".monsters.spawn", set);
		worlds.save();
	}

	public static void setAllowAnimals(String name, boolean set) {
		worlds.load();
		worlds.setProperty("worlds." + name + ".animals.spawn", set);
		worlds.save();
	}

	public static void setExceptMonsters(String name, List<String> set) {
		worlds.load();
		worlds.setProperty("worlds." + name + ".monsters.exceptfor", set);
		worlds.save();
	}

	public static void setExceptAnimals(String name, List<String> set) {
		worlds.load();
		worlds.setProperty("worlds." + name + ".animals.exceptfor", set);
		worlds.save();
	}
}
