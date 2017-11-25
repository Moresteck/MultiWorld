package bc.pl.daamazingshit;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijikokun.bukkit.Permissions.Permissions;

public class MultiWorld extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public static HashMap<String, Player> player = new HashMap<String, Player>();
	public static HashMap<Player, String> player2 = new HashMap<Player, String>();
	
	public static File langfile = new File("plugins/MultiWorld", "lang.txt");
	public static Configuration lang = new Configuration(langfile);
	
	public static File confile = new File("plugins/MultiWorld", "config.yml");
	public static Configuration conf = new Configuration(confile);
	
	@Override
	public void onDisable() {
		
		conf.save();
		log.info("[MultiWorld] MultiWorld " + getDescription().getVersion() + " disabled");
		
	}

	@Override
	public void onEnable() {
		
		log.info("[MultiWorld] MultiWorld " + getDescription().getVersion() + " enabled");
		conf.load();
		if (!langfile.exists()) {
			createOne();
		}
		if (!confile.exists()) {
			createConf();
		}
		loadLang();
		log.info("[MultiWorld] Configuration has been loaded.");
		
	}
    
    private void createConf() {
		conf.load();
		
		conf.setProperty("Worlds.world.type", "normal");;
	}

	public static void createOne() {
    	
    	lang.load();
    	lang.setProperty("lang.notEnough", "Not enough arguments!");
    	lang.setProperty("lang.worldErasedSuccessfully", "World erased successfully!");
    	lang.setProperty("lang.couldNotErase", "Could not erase world!");
    	lang.setProperty("lang.inGameOnly", "Only players can do this!");
    	lang.setProperty("lang.tpToWorld", "Teleporting to");
    	lang.setProperty("lang.nonExistingWorld", "Non-existent world specified");
    	lang.setProperty("lang.nonExistingPlayer", "Non-existent player specified");
    	lang.setProperty("lang.to", "to");
    	lang.setProperty("lang.tp", "Teleporting");
    	lang.setProperty("lang.sendTo", "send you to");
    	lang.setProperty("lang.console", "*Console*");
    	lang.setProperty("lang.worldList", "World list");
    	lang.setProperty("lang.reloaded", "Reloaded.");
    	lang.setProperty("lang.playersIn", "Players in world");
    	lang.setProperty("lang.noPermission", "You have no permission to do this!");
    	lang.setProperty("lang.creating", "Creating new world -");
    	lang.setProperty("lang.type", "type");
    	lang.setProperty("lang.created", "Created new world.");
    	lang.setProperty("lang.types", "Available types of worlds");
    	lang.setProperty("lang.removed", "Removed world successfully");
    	lang.save();
    }
	
	public static void loadLang() {
		
		lang.load();
		Lang.notEnough = lang.getString("lang.notEnough", Lang.notEnough);
    	Lang.worldErasedSuccessfully = lang.getString("lang.worldErasedSuccessfully", Lang.worldErasedSuccessfully);
    	Lang.couldNotErase = lang.getString("lang.couldNotErase", Lang.couldNotErase);
    	Lang.inGameOnly = lang.getString("lang.inGameOnly", Lang.inGameOnly);
    	Lang.tpToWorld = lang.getString("lang.tpToWorld", Lang.tpToWorld);
    	Lang.nonExistingWorld = lang.getString("lang.nonExistingWorld", Lang.nonExistingWorld);
    	Lang.nonExistingPlayer = lang.getString("lang.nonExistingPlayer", Lang.nonExistingPlayer);
    	Lang.to = lang.getString("lang.to", Lang.to);
    	Lang.tp = lang.getString("lang.tp", Lang.tp);
    	Lang.sendTo = lang.getString("lang.sendTo", Lang.sendTo);
    	Lang.console = lang.getString("lang.console", Lang.console);
    	Lang.worldList = lang.getString("lang.worldList", Lang.worldList);
    	Lang.reloaded = lang.getString("lang.reloaded", Lang.reloaded);
    	Lang.playersIn = lang.getString("lang.playersIn", Lang.playersIn);
    	Lang.noPermission = lang.getString("lang.noPermission", Lang.noPermission);
    	Lang.creating = lang.getString("lang.creating", Lang.creating);
    	Lang.type = lang.getString("lang.type", Lang.type);
    	Lang.created = lang.getString("lang.created", Lang.created);
    	Lang.types = lang.getString("lang.types", Lang.types);
    	Lang.removed = lang.getString("lang.removed", Lang.removed);
	}
	
	public static void reloadConfigs() {
		
		conf.load();
		lang.load();
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		
		Player p         = (Player)sender;
		
		ChatColor red    = ChatColor.RED;
		ChatColor green  = ChatColor.GREEN;
		ChatColor gold   = ChatColor.GOLD;
		ChatColor white  = ChatColor.WHITE;
		ChatColor blue   = ChatColor.BLUE;
		ChatColor black  = ChatColor.BLACK;
		ChatColor daqua  = ChatColor.DARK_AQUA;
		ChatColor yellow = ChatColor.YELLOW;
		ChatColor gray   = ChatColor.GRAY;
		
		if (cmd.getName().equalsIgnoreCase("mw")) {
			
			if (args.length == 0) {
				//------------------------------------------------------------------------------
				sender.sendMessage(red   + Lang.notEnough);
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw create " + blue  + "<world> (type) "   + 
						   white  + "-"   + green + " Creates new world");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw delete "   + blue  + "<world> "         + 
						   white  + "-"   + green + " Removes world from database (keeps files)");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw erase "   + blue  + "<world> "         + 
						   white  + "-"   + green + " Erases world's FILES from database");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw go "    + blue  + "<world> "         + 
						   white  + "-"   + green + " Sends you to specyfied world");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw send " + blue  + "<player> <world> " + 
						   white  + "-"   + green + " Sends someone to specyfied world");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw who "    + blue  + "(world) "         +
				           white  + "-"   + green + " Who's playing in that world?");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw list "                               + 
						   white  + "-"   + green + " Shows worldlist");
				//------------------------------------------------------------------------------
				sender.sendMessage(gold  + "  /mw reload"                            +
						   white + "-"    + green + " Reloads the configuration");
				//------------------------------------------------------------------------------
				sender.sendMessage(black + " Copyright "   + daqua + "BetaCraftNet "    + 
						   yellow + "2017");
				//------------------------------------------------------------------------------
				return true;
			}
			if (args[0].equalsIgnoreCase("create") && (sender.isOp() || Permissions.Security.has(p, "multiworld.create"))) {
				
				if (args.length == 1 || args.length == 2) {
					
					sender.sendMessage(red  + Lang.notEnough);
					sender.sendMessage(gold + "  /mw create " + blue + "<world> (type)");
					return true;
				}
				if (args[2].equalsIgnoreCase("nether")) {
					
					getServer().broadcastMessage(gold + Lang.creating + " " + 
								     yellow + args[1] + gold + ", " + Lang.type + " - " + 
								     yellow + args[2] + gold + "!");
					getServer().createWorld(args[1], Environment.NETHER);
					conf.setProperty("Worlds."+args[1], "nether");
					reloadConfigs();
					getServer().broadcastMessage(green + Lang.created);
					return true;
				}
				if (args[2].equalsIgnoreCase("normal")) {
					
					getServer().broadcastMessage(gold  + Lang.creating + " " + 
								     yellow + args[1] + gold + ", " + Lang.type + " - " + 
								     yellow + args[2] + gold + "!");
					getServer().createWorld(args[1], Environment.NORMAL);
					conf.setProperty("Worlds." + args[1] + ".type", "normal");
					reloadConfigs();
					getServer().broadcastMessage(green + Lang.created);
					return true;
				} else {
					
					sender.sendMessage(red    + Lang.types + ":");
					sender.sendMessage(green  + " normal");
					sender.sendMessage(yellow + " nether");
					return true;
				}
				
			}
			if (args[0].equalsIgnoreCase("delete") && (sender.isOp() || Permissions.Security.has(p, "multiworld.delete"))) {
				
				if (args.length == 1) {
					
					sender.sendMessage(red  + Lang.notEnough);
					sender.sendMessage(gold + "  /mw delete " + blue + "<world>");
					return true;
				}
				if (conf.getProperty("Worlds."+args[1]) != null) {
					
					conf.removeProperty("Worlds."+args[1]);
					reloadConfigs();
					sender.sendMessage(green + Lang.removed + " (\"" + 
							   gold + args[1] + green + "\")!");
					return true;
				} else {
					
					sender.sendMessage(red + Lang.nonExistingWorld + " (" + gold + args[1] + red + ")!");
					return true;
				}
			}
            if (args[0].equalsIgnoreCase("erase") && (sender.isOp() || Permissions.Security.has(p, "multiworld.erase"))) {
            	
				if (args.length == 1) {
					
					sender.sendMessage(red  + Lang.notEnough);
					sender.sendMessage(gold + "  /mw erase " + blue + "<world>");
					return true;
				}
				File file = new File(args[1]);
				if (file.exists()) {
					try {
						file.delete();
						
						if (conf.getProperty("Worlds."+args[1]) != null) {
							conf.removeProperty("Worlds."+args[1]);
							reloadConfigs();
						}
						sender.sendMessage(green + Lang.worldErasedSuccessfully + "(\"" + 
								   gold + args[1] + green + "\")!");
						return true;
					}
					catch (Exception ee) {
						sender.sendMessage(green + Lang.couldNotErase + " (\"" + 
								   gold + args[1] + green + "\")!");
					}
					return true;
				}
				else {
					
				}
			}
			if (args[0].equalsIgnoreCase("go") && (sender.isOp() || Permissions.Security.has(p, "multiworld.go"))) {
				
				if (!(sender instanceof Player)) {
					
					sender.sendMessage(Lang.inGameOnly);
					return true;
				}
				if (args.length == 1) {
					
					sender.sendMessage(red  + Lang.notEnough);
					sender.sendMessage(gold + "  /mw go " + blue + "<world>");
					return true;
				}
				if (conf.getProperty("Worlds."+args[1]) != null) {
					
					Location spawn = getServer().getWorld(args[1]).getSpawnLocation();
					p.teleportTo(spawn);
					p.sendMessage(gray + Lang.tpToWorld + gold + 
						      args[1] + gray + ".");
					return true;
				} else {
					
					sender.sendMessage(red + Lang.nonExistingWorld + " (" + gold + args[2] + red + ")!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("send") && (sender.isOp() || Permissions.Security.has(p, "multiworld.send"))) {
				
				if (args.length == 1 || args.length == 2) {
					
					sender.sendMessage(red  + Lang.notEnough);
					sender.sendMessage(gold + "  /mw send " + blue + "<player> <world>");
					return true;
				}
				
				Player target = getServer().getPlayer(args[1]);
					
				if (conf.getProperty("Worlds."+args[2]) != null) {
					
					if (target != null) {
						
						Location spawn = getServer().getWorld(args[2]).getSpawnLocation();
						target.teleportTo(spawn);
						sender.sendMessage(green + Lang.tp +" "+ yellow + 
								target.getName() + green + " " + Lang.to + gold + 
								args[2] + green + ".");
						
						if (sender == p) {
							
							target.sendMessage(yellow + p.getDisplayName() + green + 
									" " + Lang.sendTo + " " + gold + args[2] + 
									green + ".");
							return true;
						}
						if (sender != p) {
							
							target.sendMessage(yellow + Lang.console + " " + green + Lang.sendTo+ " " +
						            gold + args[2] + green + ".");
							return true;
						}
						return true;
					}
					
					else {
					
						sender.sendMessage(gold + Lang.nonExistingPlayer + " (" + args[1] + ").");
						return true;
					}
				} else {
					sender.sendMessage(red + Lang.nonExistingWorld + " ("+args[2]+").");
					return true;
				}
				
				
			}
			if (args[0].equalsIgnoreCase("list") && (sender.isOp() || Permissions.Security.has(p, "multiworld.list"))) {
				List<String> worlds = new LinkedList<String>();
				worlds.add(getServer().getWorlds().toString());
				
				sender.sendMessage(green + Lang.worldList + ":");
				sender.sendMessage(worlds.toString());
				return true;
			}
			if (args[0].equalsIgnoreCase("reload") && (sender.isOp() || 
					Permissions.Security.has(p, "multiworld.reload"))) {
				
				reloadConfigs();
				sender.sendMessage(green + Lang.reloaded);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("who") && (sender.isOp() || Permissions.Security.has(p, "multiworld.kto"))) {
				if (args.length == 1) {
					if (sender instanceof ConsoleCommandSender) {
						sender.sendMessage(red + "[MultiWorld] Choose worldname!");
						return true;
					}
					List<Player> players = new LinkedList<Player>();
					players.add(player.get(p.getWorld().getName()));
					for (Player p1 : players) {
						p.sendMessage(gold + Lang.playersIn + p.getWorld().getName());
						p.sendMessage(green + p1.toString());
						return true;
					}
					
				}
				if (getServer().getWorld(args[1]) != null) {
					List<Player> players = new LinkedList<Player>();
					players.add(player.get(args[1]));
					for (Player p1 : players) {
						sender.sendMessage(gold + Lang.playersIn + p.getWorld().getName());
						sender.sendMessage(green + p1.toString());
						return true;
					}
				}
				else {
					sender.sendMessage(red + "[MultiWorld] " + Lang.nonExistingWorld);
				}
			}
			else {
				
				sender.sendMessage(red + Lang.noPermission);
				return true;
			}
		}
		return true;
	}
}
