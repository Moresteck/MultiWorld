package pl.moresteck.multiworld;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import pl.moresteck.bvnpe.BukkitVersion;
import pl.moresteck.multiworld.commands.MCreate;
import pl.moresteck.multiworld.commands.MHelp;
import pl.moresteck.multiworld.commands.MSetSpawn;
import pl.moresteck.multiworld.commands.MTeleport;
import pl.moresteck.multiworld.listeners.listener;
import pl.moresteck.multiworld.listeners.worldlistener;
import pl.moresteck.multiworld.world.MWorld;
import pl.moresteck.multiworld.world.MWorldConfig;

public class MultiWorld extends JavaPlugin {
	public static MLogger log = new MLogger("Minecraft");
	public static Server server;
	public static Set<MWorld> worlds = new HashSet<MWorld>();
	public static String version;
	public static MultiWorld instance;

	public void onEnable() {
		BukkitVersion.setupVersion((CraftServer) this.getServer());

		instance = this;
		server = this.getServer();
		version = this.getDescription().getVersion();

		// b1.3+
		if (BukkitVersion.getVersionId() >= 2) {
			server.getPluginManager().registerEvent(Type.WORLD_LOAD, new worldlistener().new v13(), Priority.Normal, this);
		} else {
			server.getPluginManager().registerEvent(Type.WORLD_LOADED, new worldlistener().new v12(), Priority.Normal, this);
		}
		Perm.setPermissions(server.getPluginManager().getPlugin("Permissions"));
	}

	public void init() {
		// Just to not repeat the message.
		// BukkitVersion PE enables on startup, so it loads before MultiWorld.
		if (!server.getPluginManager().isPluginEnabled("BukkitVersion")) {
			log.info(true, "BV: enabled v" + BukkitVersion.plugin_version);
			log.info(true, "BV: This server runs on Minecraft Beta " + BukkitVersion.getVersion());
			if (BukkitVersion.getVersionId() == 0) {
				log.info("BV: Woah! How did you get this Bukkit version?");
				log.info("BV: If it isn't a recompile, fake or test version, please send it to me on Discord: Moresteck#1688");
			}
		}

		log.info("[MultiWorld] Enabled v" + version);
		log.info("[MultiWorld] Always use version's latest build of Craftbukkit for the best compatibility!");
		log.info("[MultiWorld] Download it here: https://betacraft.ovh/bukkit");
		// No compatibility for b1.1 because it doesn't contain any multi-world code to hook in.
		if (BukkitVersion.getVersionId() == 0) {
			log.info("[MultiWorld] No support for Beta 1.1, disabling...");
			this.setEnabled(false);
			return;
		}
		for (String worldname: MWorldConfig.getWorlds()) {
			this.loadWorld(worldname);
		}
	}

	public void onDisable() {
		log.info(true, "[MultiWorld] Saving worlds...");
		for (MWorld world: worlds) {
			saveWorld(world.getWorld());
		}
		log.info("[MultiWorld] Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
		if (args.length > 0) {
			new MHelp(cmd, cs, args).execute();
			new MCreate(cmd, cs, args).execute();
			new MTeleport(cmd, cs, args).execute();
			new MSetSpawn(cmd, cs, args).execute();
		} else if (args.length == 0) {
			new MHelp(cmd, cs, args).execute();
		}
		if (cmd.getName().equalsIgnoreCase("testentity")) {
			World w = server.getWorld("world");
			if (args.length == 0) {
				for (Entity e: w.getEntities()) {
					cs.sendMessage(getName(e));
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("xd")) {
				for (Entity e: w.getEntities()) {
					e.remove();
				}
				return true;
			}
		}
		return true;
	}

	public static void saveWorld(World world) {
		// b1.3+
		if (BukkitVersion.getVersionId() >= 2) {
			world.save();
		} else {
			((CraftWorld)world).getHandle().a(true, null);
		}
		log.info(true, "[MultiWorld] \"" + world.getName() + "\" saved.");
	}

	public static MWorld getWorld(String name) {
		for (MWorld world: worlds) {
			if (world.getName().equalsIgnoreCase(name)) {
				return world;
			}
		}
		return null;
	}

	protected void loadWorld(String worldname) {
		MWorld world = new MWorld(worldname);
		// If the version is b1.6.6 or higher...
		if (BukkitVersion.getVersionId() >= 9) {
			if (!world.getGenerator().equals("")) {
				String gen;
				String genargs;
				try {
					String[] arr = world.getGenerator().split(":");
					gen = arr[0];
					genargs = arr[1];
				} catch (Exception ex) {
					log.info("[MultiWorld] Generator must be specified as "
							+ ChatColor.GOLD + "GeneratorName" + ChatColor.WHITE + ":"
							+ ChatColor.GOLD + "GeneratorArguments" + ChatColor.WHITE + " in " + worldname);
					return;
				}
				ChunkGenerator generator = null;
				try {
					generator = MultiWorld.server.getPluginManager().getPlugin(gen).
					getDefaultWorldGenerator(worldname, genargs);
				} catch (Exception ex) {
					log.info("[MultiWorld] Wrong generator name or generator arguments in " + worldname);
					return;
				}
				World bworld = server.getWorld(worldname) == null ? server.createWorld(worldname, world.getEnvironment(),
						world.getSeed(), generator) : server.getWorld(worldname);
				log.info("[MultiWorld] Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
				bworld.setSpawnFlags(world.getAllowMonsters(), world.getAllowAnimals());
				bworld.setPVP(world.getPvP());
			} else {
				World bworld = server.getWorld(worldname) == null ? server.createWorld(worldname, world.getEnvironment(),
						world.getSeed()) : server.getWorld(worldname);
				log.info("[MultiWorld] Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
				bworld.setSpawnFlags(world.getAllowMonsters(), world.getAllowAnimals());
				bworld.setPVP(world.getPvP());
			}
		} else {
			World bworld = null;
			// If the version is b1.3 or b1.2_01...
			if (BukkitVersion.getVersionId() <= 2) {
				bworld = server.getWorld(worldname) == null ? 
						server.createWorld(worldname, world.getEnvironment()) :
						server.getWorld(worldname);
				log.info("[MultiWorld] Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
				// To control PVP
				if (BukkitVersion.getVersionId() == 2) { // b1.3
					BukkitVersion.registerEventSafely(this, "ENTITY_DAMAGE", new listener());
				} else { // b1.2_01
					BukkitVersion.registerEventSafely(this, "ENTITY_DAMAGED", new listener());
				}
				// To control spawn flags
				BukkitVersion.registerEventSafely(this, "CREATURE_SPAWN", new listener());
			// Else, the version is between b1.4 and b1.6.5.
			} else {
				bworld = server.getWorld(worldname) == null ? 
						server.createWorld(worldname, world.getEnvironment(),
						world.getSeed()) : server.getWorld(worldname);
				log.info("[MultiWorld] Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
				// If the version is b1.4...
				if (BukkitVersion.getVersionId() == 3) {
					// To control spawn flags and PVP
					BukkitVersion.registerEventSafely(this, "CREATURE_SPAWN", new listener());
					BukkitVersion.registerEventSafely(this, "ENTITY_DAMAGE", new listener());
				}
				// If the version is between b1.5_02 and b1.6.5...
				if (BukkitVersion.getVersionId() >= 4) {
					bworld.setPVP(world.getPvP());
					// To control spawn flags
					BukkitVersion.registerEventSafely(this, "CREATURE_SPAWN", new listener());
				}
			}
		}
		worlds.add(world);
	}

	public static String getName(Entity e) {
		return e.getClass().getSimpleName().substring(5).toUpperCase();
	}
}