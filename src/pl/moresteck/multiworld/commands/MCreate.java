package pl.moresteck.multiworld.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

import pl.moresteck.bvnpe.BukkitVersion;
import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;
import pl.moresteck.multiworld.world.MWorldConfig;

public class MCreate extends MCommand {

	public MCreate(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.world.create");
	}

	public void execute() {
		boolean create = this.exists(0, "create");
		if (!create) {
			return;
		}
		int arg = args.length;
		if (arg == 1 || arg == 2) {
			this.displayCommandHelp();
			return;
		}
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}
		String name = args[1];
		if (MultiWorld.server.getWorld(name) != null) {
			this.send("A world with this name is already loaded: " + ChatColor.RED + name);
			return;
		}
		Environment env;
		try {
			env = Environment.valueOf(args[2].toUpperCase());
		} catch (Exception ex) {
			this.send("Environment must be one of these values: " + ChatColor.GREEN + "normal " + ChatColor.RED + "nether" + (BukkitVersion.getVersionId() >= 9 ? ChatColor.AQUA + " skylands" : ""));
			return;
		}
		World bworld = null;
		MWorld world = new MWorld(name);
		// b1.4+
		if (BukkitVersion.getVersionId() >= 3) {
			long seed;
			if (args.length >= 4) {
				try {
					seed = Long.parseLong(args[3]);
				} catch (Exception x) {
					if (args[3].equals("-random")) {
						seed = new Random().nextLong();
					} else {
						seed = args[3].hashCode();
					}
				}
			} else {
				seed = new Random().nextLong();
			}
			// b1.6.6+
			if (BukkitVersion.getVersionId() >= 9) {
				ChunkGenerator generator = null;
				if (args.length >= 5) {
					String gen;
					String genargs;
					try {
						String[] arr = args[4].split(":");
						gen = arr[0];
						genargs = arr[1];
					} catch (Exception ex) {
						this.send("Generator must be specified as "
								+ ChatColor.GOLD + "GeneratorName" + ChatColor.WHITE + ":"
								+ ChatColor.GOLD + "GeneratorArguments");
						return;
					}
					try {
						generator = MultiWorld.server.getPluginManager().getPlugin(gen).
						getDefaultWorldGenerator(name, genargs);
					} catch (Exception ex) {
						this.send("Wrong generator name or generator arguments: " + args[4]);
						return;
					}
				}
				this.send("Creating new world...");
				if (BukkitVersion.isVersionHigh()) {
					org.bukkit.WorldCreator creator = new org.bukkit.WorldCreator(name);
					creator.environment(env);
					creator.generator(generator);
					creator.seed(seed);
					bworld = MultiWorld.server.createWorld(creator);
				} else {
					bworld = MultiWorld.server.createWorld(name, env, seed, generator);
				}

				MWorldConfig.createBasicConfig(name, env.name());
				if (generator != null) {
					MWorldConfig.setGenerator(name, args[4]);
				}
				MultiWorld.log.info("[MultiWorld] Created world \"" + name + "\" (Seed: "
						+ bworld.getSeed() + ")");
				bworld.setSpawnFlags(world.getAllowMonsters(), world.getAllowAnimals());
				bworld.setPVP(world.getPvP());
				this.send("Created new world - " + MList.getColor(world) + world.getName());
			// b1.4 - b1.6.5
			} else {
				this.send("Creating new world...");
				bworld = MultiWorld.server.createWorld(name, env, seed);
				MWorldConfig.createBasicConfig(name, env.name());
				MultiWorld.log.info("[MultiWorld] Created world \"" + name + "\" (Seed: "
						+ bworld.getSeed() + ")");
				if (BukkitVersion.getVersionId() >= 4) bworld.setPVP(world.getPvP());
				this.send("Created new world - " + MList.getColor(world) + world.getName());
			}
		// b1.3-
		} else {
			this.send("Creating new world...");
			bworld = MultiWorld.server.createWorld(name, env);
			MWorldConfig.createBasicConfig(name, env.name());
			MultiWorld.log.info("[MultiWorld] Created world \"" + name + "\" (Seed: "
					+ world.getSeed() + ")");
			this.send("Created new world - " + MList.getColor(world) + world.getName());
		}
		MultiWorld.worlds.add(world);
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		int id = BukkitVersion.getVersionId();
		// b1.6.6+
		if (id >= 9) {
			this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[seed]" + ChatColor.BLUE + " [generator]");
			this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - New world's name, e.g.: " + ChatColor.YELLOW + "survival");
			this.send(ChatColor.DARK_GRAY + " environment" + ChatColor.WHITE + " - Environment: " + ChatColor.GREEN + "normal " + ChatColor.RED + "nether " + ChatColor.AQUA + "skylands");
			this.send(ChatColor.DARK_GRAY + " seed" + ChatColor.WHITE + " - Seed, e.g.: " + ChatColor.GOLD + "gargamel" + ChatColor.WHITE + ", " + ChatColor.BLUE + "-random" + ChatColor.WHITE + " creates a random seed");
			this.send(ChatColor.DARK_GRAY + " generator" + ChatColor.WHITE + " - Generator arguments, e.g.: " + ChatColor.GOLD + "CleanroomGenerator" + ChatColor.WHITE + ":" + ChatColor.GOLD + ".");
		// b1.4 - b1.6.5
		} else if (id >= 3) {
			this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[seed]");
			this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - New world's name, e.g.: " + ChatColor.YELLOW + "survival");
			this.send(ChatColor.DARK_GRAY + " environment" + ChatColor.WHITE + " - Environment: " + ChatColor.GREEN + "normal " + ChatColor.RED + "nether");
			this.send(ChatColor.DARK_GRAY + " seed" + ChatColor.WHITE + " - Seed, e.g.: " + ChatColor.GOLD + "gargamel" + ChatColor.WHITE + ", " + ChatColor.BLUE + "-random" + ChatColor.WHITE + " creates a random seed");
		// b1.3-
		} else {
			this.send(ChatColor.BLUE + "/mw create " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
			this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - New world's name, e.g.: " + ChatColor.YELLOW + "survival");
			this.send(ChatColor.DARK_GRAY + " environment" + ChatColor.WHITE + " - Environment: " + ChatColor.GREEN + "normal " + ChatColor.RED + "nether");
		}
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Creates new world using given arguments");
	}
}
