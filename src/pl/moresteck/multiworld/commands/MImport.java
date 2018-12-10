package pl.moresteck.multiworld.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

import pl.moresteck.bvie.BukkitVersion;
import pl.moresteck.multiworld.MultiWorld;
import pl.moresteck.multiworld.world.MWorld;
import pl.moresteck.multiworld.world.MWorldConfig;

public class MImport extends MCommand {

	public MImport(Command cmd, CommandSender cs, String[] args) {
		super(cmd, cs, args);
		this.setPermission("multiworld.world.import");
	}

	public void execute() {
		boolean importt = this.exists(0, "import");
		if (!importt) {
			return;
		}
		int arg = args.length;
		if (arg == 1 || arg == 2) {
			this.displayCommandHelp();
			return;
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				this.displayCommandHelp();
				return;
			}
		}
		if (!this.hasPermission()) {
			this.send("No permission!");
			return;
		}
		String name = args[1];
		if (MultiWorld.getWorld(name) != null) {
			this.send("A world with this name is already loaded: " + ChatColor.RED + name);
			return;
		}
		File file = new File(".", name);
		if (!file.exists()) {
			this.send("There are no world files for this name.");
			this.send("Maybe try using " + ChatColor.YELLOW + "/mw create " + ChatColor.WHITE + "?");
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
		// 1.6.6+
		if (BukkitVersion.getVersionId() >= 9) {
			ChunkGenerator generator = null;
			if (args.length >= 4) {
				String gen;
				String genargs;
				try {
					String[] arr = args[3].split(":");
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
					this.send("Wrong generator name or generator arguments: " + args[3]);
					return;
				}
			}
			this.send("Importing world...");
			if (BukkitVersion.isVersionHigh()) {
				org.bukkit.WorldCreator creator = new org.bukkit.WorldCreator(name);
				creator.environment(env);
				creator.generator(generator);
				bworld = MultiWorld.server.createWorld(creator);
			} else {
				bworld = MultiWorld.server.createWorld(name, env, generator);
			}

			MWorldConfig.createBasicConfig(name, env.name());
			if (generator != null) {
				MWorldConfig.setGenerator(name, args[3]);
			}
			MultiWorld.log.info("[MultiWorld] Imported world \"" + name + "\" (Seed: "
					+ world.getSeed() + ")");
			bworld.setSpawnFlags(world.getAllowMonsters(), world.getAllowAnimals());
			bworld.setPVP(world.getPvP());
			this.send("Imported world - " + MList.getColor(world) + world.getName());
		} else {
			this.send("Importing world...");
			bworld = MultiWorld.server.createWorld(name, env);

			MWorldConfig.createBasicConfig(name, env.name());
			MultiWorld.log.info("[MultiWorld] Imported world \"" + name + "\" (Seed: "
					+ world.getSeed() + ")");
			if (BukkitVersion.getVersionId() >= 4) bworld.setPVP(world.getPvP());
			this.send("Imported world - " + MList.getColor(world) + world.getName());
		}
		MultiWorld.worlds.add(world);
	}

	public void displayCommandHelp() {
		this.send(ChatColor.GREEN + "====== Command Help ======");
		int id = BukkitVersion.getVersionId();
		// b1.6.6+
		if (id >= 9) {
			this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment> " + ChatColor.GRAY + "[generator]");
			this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - World's name, e.g.: " + ChatColor.YELLOW + "survival");
			this.send(ChatColor.DARK_GRAY + " environment" + ChatColor.WHITE + " - Environment: " + ChatColor.GREEN + "normal " + ChatColor.RED + "nether " + ChatColor.AQUA + "skylands");
			this.send(ChatColor.DARK_GRAY + " generator" + ChatColor.WHITE + " - Generator arguments, e.g.: " + ChatColor.GOLD + "CleanroomGenerator" + ChatColor.WHITE + ":" + ChatColor.GOLD + ".");
		// b1.3-
		} else {
			this.send(ChatColor.BLUE + "/mw import " + ChatColor.GRAY + "<world_name>" + ChatColor.BLUE + " <environment>");
			this.send(ChatColor.DARK_GRAY + " world_name" + ChatColor.WHITE + " - World's name, e.g.: " + ChatColor.YELLOW + "survival");
			this.send(ChatColor.DARK_GRAY + " environment" + ChatColor.WHITE + " - Environment: " + ChatColor.GREEN + "normal " + ChatColor.RED + "nether");
		}
		this.send(" ");
		this.send(ChatColor.DARK_AQUA + "Permission: " + (this.hasPermission() ? ChatColor.GREEN : ChatColor.RED) + this.perm);
		this.send(ChatColor.DARK_AQUA + "Info: " + ChatColor.WHITE + "Imports a world using given arguments");
	}
}
