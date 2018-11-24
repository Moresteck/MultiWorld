package pl.moresteck.multiworld;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import pl.moresteck.bvnpe.BukkitVersion;
import pl.moresteck.multiworld.listeners.listener;
import pl.moresteck.multiworld.world.MWorld;
import pl.moresteck.multiworld.world.MWorldConfig;

public class MultiWorld extends JavaPlugin {
	static MLogger log = new MLogger("Minecraft");
	public static Server server;
	public static Set<MWorld> worlds = new HashSet<MWorld>();

	public void onEnable() {
		BukkitVersion.setupVersion((CraftServer) this.getServer());
		// Just to not repeat the message.
		try {
			pl.moresteck.bv.BukkitVersion.getVersion();
		} catch (Exception ex) {
			log.info(true, "BV: enabled v" + BukkitVersion.plugin_version);
			log.info(true, "BV: This server runs on Minecraft Beta " + BukkitVersion.getVersion());
			if (BukkitVersion.getVersionId() <= 0) {
				log.info("BV: Woah! How did you get this version?");
				log.info("BV: If it isn't a recompile, fake or test version, please send it to me on Discord: Moresteck#1688");
			}
		}
		
		log.info(" [MultiWorld]  Enabled v" + this.getDescription().getVersion());
		log.info("[MultiWorld]  Always use the latest version build of Craftbukkit for the best compatibility!");
		log.info("[MultiWorld]  Download them here: https://betacraft.ovh/bukkit");
		// No support for 1.1 because it doesn't contain any multi-world code to hook in.
		if (BukkitVersion.getVersionId() <= 0) {
			log.info(" [MultiWorld]  No support for Beta 1.1, disabling...");
			this.setEnabled(false);
			return;
		}
		server = this.getServer();
		for (String worldname: MWorldConfig.getWorlds()) {
			this.loadWorld(worldname);
		}
	}

	public void onDisable() {
		log.info(true, " [MultiWorld]  Saving worlds...");
		for (MWorld world: worlds) {
			if (BukkitVersion.getVersionId() >= 9) {
				world.getWorld().save();
			} else {
				((CraftWorld)world.getWorld()).getHandle().a(true, null);
			}
			log.info(true, " [MultiWorld]  \"" + world.getName() + "\" saved.");
		}
		log.info(" [MultiWorld]  Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
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

	protected void loadWorld(String worldname) {
		MWorld world = new MWorld(worldname);
		// If the version is 1.6.6 or higher...
		if (BukkitVersion.getVersionId() >= 9) {
			// TODO Use generator
			World bworld = server.getWorld(worldname) == null ? server.createWorld(worldname, world.getEnvironment(),
					world.getSeed()) : server.getWorld(worldname);
			log.info(" [MultiWorld]  Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
			bworld.setSpawnFlags(world.getAllowMonsters(), world.getAllowAnimals());
			bworld.setPVP(world.getPvP());
		} else {
			World bworld = null;
			// If the version is 1.3 or 1.2_01...
			if (BukkitVersion.getVersionId() <= 2) {
				bworld = server.getWorld(worldname) == null ? 
						server.createWorld(worldname, world.getEnvironment()) :
						server.getWorld(worldname);
				log.info(" [MultiWorld]  Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
				// To control spawn flags and PVP
				if (BukkitVersion.getVersionId() == 2) { // 1.3
					BukkitVersion.registerEventSafely(this, "ENTITY_DAMAGE", new listener());
				} else { // 1.2_01
					BukkitVersion.registerEventSafely(this, "ENTITY_DAMAGED", new listener());
				}
				// both
				BukkitVersion.registerEventSafely(this, "CREATURE_SPAWN", new listener());
			// Else if the version is between 1.4 and 1.6.5...
			} else {
				bworld = server.getWorld(worldname) == null ? 
						server.createWorld(worldname, world.getEnvironment(),
						world.getSeed()) : server.getWorld(worldname);
				log.info(" [MultiWorld]  Loaded world \"" + worldname + "\" (Seed: " + world.getSeed() + ")");
				// If the version is 1.4...
				if (BukkitVersion.getVersionId() == 3) {
					// To control spawn flags and PVP
					BukkitVersion.registerEventSafely(this, "CREATURE_SPAWN", new listener());
					BukkitVersion.registerEventSafely(this, "ENTITY_DAMAGE", new listener());
				}
				// If the version is between 1.5_02 and 1.6.5...
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