package pl.daamazingshit.mw.managers;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldSaveEvent;

import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldServer;
import pl.daamazingshit.mw.mw;
import pl.daamazingshit.mw.util.PropertyType;

public class WorldManager {

    private long seed;
    private static String name;
    private Environment env;

	public WorldManager(String worldname) {
		name = worldname;
		this.seed = (new Random()).nextLong();
		this.env  = Environment.NORMAL;
	}

	public WorldManager(String worldname, long seed) {
		name = worldname;
		this.seed = seed;
		this.env  = Environment.NORMAL;
	}

	public WorldManager(String worldname, Environment environment) {
		name = worldname;
		this.seed = (new Random()).nextLong();
		this.env  = environment;
	}

	public WorldManager(String worldname, Environment environment, long seed) {
		name = worldname;
		this.seed = seed;
		this.env  = environment;
	}

	public Boolean create() {
		try {
			mw.instance.getServer().createWorld(name, env, seed);
			ConfigWorld.add(name, env, true, true, true, true, "true", true, true, true, seed);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Used for loading worlds.
	 */
	public void setup() {
		mw.instance.getServer().createWorld(name, env, seed);
	}

	public World createWorld() {
		ConfigWorld.add(name, env, true, true, true, true, "true", true, true, true, seed);
		return mw.instance.getServer().createWorld(name, env, seed);
	}

	public void remove() {
		ConfigWorld.remove(name);
		this.unloadWorld(true);
	}

	public Long seed() {
		return this.world().getId();
	}

	public String name() {
		return this.world().getName();
	}

	public Environment dimension() {
		return this.world().getEnvironment();
	}

	public World world() {
		if (mw.instance.getServer().getWorld(name) == null) {
			return null;
		}
		World ret = mw.instance.getServer().getWorld(name);
		return ret;
	}

	public Boolean allowMonsters() {
		return ConfigWorld.getAllow(PropertyType.MONSTERS, name);
	}

	public Boolean allowAnimals() {
		return ConfigWorld.getAllow(PropertyType.ANIMALS, name);
	}

	public Boolean allowPVP() {
		return ConfigWorld.getAllow(PropertyType.PVP, name);
	}

	public List<Player> players() {
		return this.world().getPlayers();
	}

	public List<Entity> entities() {
		List<Entity> ret = new LinkedList<Entity>();
		ret.addAll(this.world().getEntities());
		for (Entity ent: ret) {
			if (!(ent instanceof Item)) {
				ret.remove(ent);
			}
		}
		return ret.isEmpty() ? null : ret;
	}

	public List<Entity> living() {
		List<Entity> ret = new LinkedList<Entity>();
		ret.addAll(this.world().getLivingEntities());
		for (Entity ent: ret) {
			if (!(ent instanceof LivingEntity)) {
				ret.remove(ent);
			}
		}
		return ret.isEmpty() ? null : ret;
	}

	public Location spawn() {
		return this.world().getSpawnLocation();
	}

	public Boolean setspawn(Location loc) {
		try {
			this.world().setSpawnLocation(loc.getBlockX(), loc.getBlockX(), loc.getBlockZ());
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	// Code borrowed from Craftbukkit https://github.com/Bukkit/CraftBukkit
	@SuppressWarnings("unlikely-arg-type")
	public boolean unloadWorld(boolean save) {
		World world = world();
        if (world == null) {
            return false;
        }

        WorldServer handle = ((CraftWorld) world).getHandle();

        if (!(console().worlds.contains(handle))) {
            return false;
        }

        if (!(handle.dimension > 1)) {
            return false;
        }

        if (handle.b.size() > 0) {
            return false;
        }

        if (save) {
        	WorldSaveEvent e = new WorldSaveEvent(world);
        	mw.instance.getServer().getPluginManager().callEvent(e);
        	
            handle.a(true, (IProgressUpdate) null);
            handle.r();
        }

        mw.instance.getServer().getWorlds().remove(world.getName().toLowerCase());
        console().worlds.remove(console().worlds.indexOf(handle));

        return true;
    }
	// End of borrowed code

	public MinecraftServer console() {
		CraftServer cs = (CraftServer) mw.instance.getServer();
		
		Field f;
		try {
			f = CraftServer.class.getDeclaredField("console");
		}
		catch (Exception ex) {
			return null;
		}
		
		MinecraftServer ms;
		try {
			f.setAccessible(true);
			ms = (MinecraftServer) f.get(cs);
		}
		catch (Exception ex) {
			return null;
		}
		return ms;
	}
}