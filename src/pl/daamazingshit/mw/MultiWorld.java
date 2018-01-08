package pl.daamazingshit.mw;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.daamazingshit.mw.listeners.Entities;
import pl.daamazingshit.mw.managers.ConfigWorld;
import pl.daamazingshit.mw.managers.Help;
import pl.daamazingshit.mw.managers.WorldManager;
import pl.daamazingshit.mw.util.PropertyType;
import pl.daamazingshit.mw.util.Sender;

public class MultiWorld extends JavaPlugin {

	public static Plugin instance;
	public void onDisable() {}

	@Override
	public void onEnable() {
		instance = this;
		System.out.print("MultiWorld enabled.");
		
		loadWorlds();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Type.CREATURE_SPAWN, new Entities(), Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DAMAGE, new Entities(), Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_EXPLODE, new Entities(), Priority.Highest, this);
	}

	public static void loadWorlds() {
		World w = instance.getServer().getWorld("world");
		if (ConfigWorld.getWorldList() == null) {
			ConfigWorld.add("world", Environment.NORMAL, true, true, true, w.getId());
		}
		for (WorldManager world: ConfigWorld.getWorldList()) {
			world.setup();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdalias, String[] args) {
		Sender s = new Sender(sender);
		if (cmd.getName().equalsIgnoreCase("mw")) {
			if (args.length == 0) {
				Help.showHelp(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("create")) {
				if (!s.isAuthorized("multiworld.manage.create")) {
					return true;
				}
				if (args.length == 1) {
					Help.showHelp(sender);
					return true;
				}
				WorldManager wm = null;
				// If create command contains world's name
				if (args.length == 2) {
					wm = new WorldManager(args[1]);
				}
				// If create command contains world's name and environment or world's seed
				if (args.length == 3) {
					try {
						// Done if argument 3 is environment
						Environment env = Environment.valueOf(args[2].toUpperCase());
						wm = new WorldManager(args[1], env);
					}
					catch (Exception ex) {
						// Done if argument 3 is seed
						wm = new WorldManager(args[1], Long.parseLong(args[2]));
					}
				}
				// If create command contains world's name, environment and seed
				if (args.length == 4) {
					try {
						wm = new WorldManager(args[1], Environment.valueOf(args[2].toUpperCase()), Long.parseLong(args[3]));
					}
					catch (Exception ex) {
						if (s.isPlayer()) {
						    sender.sendMessage("§cInvalid format.");
						} if (!s.isPlayer()) {
						    sender.sendMessage("Invalid format."); }
						Help.showHelp(sender);
						return true;
					}
				}
				if (wm == null) {
					if (s.isPlayer()) {
					    sender.sendMessage("§cSomething went wrong! Please try again.");
					} if (!s.isPlayer()) {
					    sender.sendMessage("Something went wrong! Please try again."); }
					return true;
				}
				if (s.isPlayer()) {
					Bukkit.getServer().broadcastMessage("§aTrying to create a new world: " + args[1]);
				} if (!s.isPlayer()) {
					Bukkit.getServer().broadcastMessage("§aTrying to create a new world: " + args[1]);
					sender.sendMessage("Trying to create a new world: " + args[1]); }
				boolean done = wm.create();
				if (s.isPlayer()) {
					sender.sendMessage(done == true ? "§aComplete." : "§cSomething went wrong!");
				} if (!s.isPlayer()) {
					sender.sendMessage(done == true ? "Complete." : "Something went wrong!"); }
				return true;
			}
			if (args[0].equalsIgnoreCase("remove")) {
				if (args.length == 1) {
					return true;
				}
				
			}
			else {
				Help.showHelp(sender);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("worldsettings")) {
			if (!s.isAuthorized("multiworld.settings")) {
				return true;
			}
			if (args.length == 0) {
				Help.showSettingsHelp(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("animals")) {
				if (args.length == 1) {
					if (s.isPlayer()) {
					    sender.sendMessage("§cNot enough arguments.");
					} if (!s.isPlayer()) {
					    sender.sendMessage("Not enough arguments"); }
					Help.showSettingsHelp(sender);
					return true;
				}
				boolean animals = Boolean.parseBoolean(args[1].toLowerCase());
				if (args.length == 3) {
					boolean done = ConfigWorld.setAllow(PropertyType.ANIMALS, args[2], animals);
					if (s.isPlayer()) {
					    sender.sendMessage(done == true ? "§aSuccess! §fSet the property '§banimals§f' to '§c" + animals + "§f' in world '§a"+args[2]+"§f'" : "§cSomething went wrong!"); }
					if (!s.isPlayer()) {
					    sender.sendMessage(done == true ? "Success! Set the property 'animals' to '" + animals + "' in world '"+args[2]+"'" : "Something went wrong!"); }
					return true;
				}
				if (args.length == 2) {
					if (!s.isPlayer()) {
						sender.sendMessage("Not enough arguments");
						return true; }
					String world = ((Player)sender).getWorld().getName();
					boolean done = ConfigWorld.setAllow(PropertyType.ANIMALS, world, animals);
					if (s.isPlayer()) {
					    sender.sendMessage(done == true ? "§aSuccess! §fSet the property '§banimals§f' to '§c" + animals + "§f' in world '§a"+world+"§f'" : "§cSomething went wrong!"); }
					if (!s.isPlayer()) {
						sender.sendMessage(done == true ? "Success! Set the property 'animals' to '" + animals + "' in world '"+world+"'" : "Something went wrong!"); }
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("monsters")) {
				if (args.length == 1) {
					if (s.isPlayer()) {
					    sender.sendMessage("§cNot enough arguments.");
					} if (!s.isPlayer()) {
					    sender.sendMessage("Not enough arguments"); }
					Help.showSettingsHelp(sender);
					return true;
				}
				boolean monsters = Boolean.parseBoolean(args[1].toLowerCase());
				if (args.length == 3) {
					boolean done = ConfigWorld.setAllow(PropertyType.MONSTERS, args[2], monsters);
					if (s.isPlayer()) {
					    sender.sendMessage(done == true ? "§aSuccess! §fSet the property '§bmonsters§f' to '§c" + monsters + "§f' in world '§a"+args[2]+"§f'" : "§cSomething went wrong!"); }
					if (!s.isPlayer()) {
					    sender.sendMessage(done == true ? "Success! Set the property 'monsters' to '" + monsters + "' in world '"+args[2]+"'" : "Something went wrong!"); }
					return true;
				}
				if (args.length == 2) {
					if (!s.isPlayer()) {
						sender.sendMessage("Not enough arguments");
						return true; }
					String world = ((Player)sender).getWorld().getName();
					boolean done = ConfigWorld.setAllow(PropertyType.MONSTERS, world, monsters);
					sender.sendMessage(done == true ? "§aSuccess! §fSet the property '§bmonsters§f' to '§c" + monsters + "§f' in world '§a"+world+"§f'" : "§cSomething went wrong!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("pvp")) {
				if (args.length == 1) {
					if (s.isPlayer()) {
					    sender.sendMessage("§cNot enough arguments.");
					} if (!s.isPlayer()) {
					    sender.sendMessage("Not enough arguments"); }
					Help.showSettingsHelp(sender);
					return true;
				}
				boolean pvp = Boolean.parseBoolean(args[1].toLowerCase());
				if (args.length == 3) {
					boolean done = ConfigWorld.setAllow(PropertyType.PVP, args[2], pvp);
					if (s.isPlayer()) {
					    sender.sendMessage(done == true ? "§aSuccess! §fSet the property '§bpvp§f' to '§c" + pvp + "§f' in world '§a"+args[2]+"§f'" : "§cSomething went wrong!"); }
					if (!s.isPlayer()) {
					    sender.sendMessage(done == true ? "Success! Set the property 'pvp' to '" + pvp + "' in world '"+args[2]+"'" : "Something went wrong!"); }
					return true;
				}
				if (args.length == 2) {
					if (!s.isPlayer()) {
						sender.sendMessage("Not enough arguments");
						return true; }
					String world = ((Player)sender).getWorld().getName();
					boolean done = ConfigWorld.setAllow(PropertyType.PVP, world, pvp);
					sender.sendMessage(done == true ? "§aSuccess! §fSet the property '§bpvp§f' to '§c" + pvp + "§f' in world '§a"+world+"§f'" : "§cSomething went wrong!");
					return true;
				}
			}
		}
		return true;
	}
}
